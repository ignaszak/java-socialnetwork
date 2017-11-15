package net.ignaszak.socialnetwork.service.media;

import net.ignaszak.socialnetwork.domain.Media;
import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.MediaUploadException;
import net.ignaszak.socialnetwork.model.image.Image;
import net.ignaszak.socialnetwork.model.image.ImageException;
import net.ignaszak.socialnetwork.repository.MediaRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Service
public class MediaServiceImpl implements MediaService {

    private MediaRepository mediaRepository;
    private Image image;
    private Path uploadsLocation;
    private Path tempLocation;
    private int profileThumbnailWidth;
    private int profileThumbnailHeight;
    private int thumbnailWidth;
    private int thumbnailHeight;

    @Autowired
    public void setMediaRepository(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    @Autowired
    public void setImage(Image image) {
        this.image = image;
    }

    @Value("${app.uploads.path}")
    public void setUploadsLocation(String uploadsLocation) {
        this.uploadsLocation = Paths.get(uploadsLocation);
    }

    @Value("${app.uploads.temp.path}")
    public void setTempLocation(String tempLocation) {
        this.tempLocation = Paths.get(tempLocation);
    }

    @Value("${app.profile.thumbnail.width}")
    public void setProfileThumbnailWidth(int profileThumbnailWidth) {
        this.profileThumbnailWidth = profileThumbnailWidth;
    }

    @Value("${app.profile.thumbnail.height}")
    public void setProfileThumbnailHeight(int profileThumbnailHeight) {
        this.profileThumbnailHeight = profileThumbnailHeight;
    }

    @Value("${app.thumbnail.width}")
    public void setThumbnailWidth(int thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    @Value("${app.thumbnail.height}")
    public void setThumbnailHeight(int thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    @Override
    public Page<Media> getByUser(User user, Pageable page) {
        return mediaRepository.findAllByAuthorOrderByCreatedDate(user, page);
    }

    @Override
    public Media saveProfileImageWithUser(MultipartFile file, User user) {
        try {
            String newName = getUniqueName(file);
            Media media = new Media(newName);
            Image image = storeFile(file, newName);
            image.createProfileThumbnail(profileThumbnailWidth, profileThumbnailHeight);
            user.setProfile(newName);
            media.setAuthor(user);
            mediaRepository.save(media);
            return media;
        } catch (ImageException e) {
            throw new MediaUploadException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Media saveTempImageWithUserAndKey(MultipartFile file, User user, Integer key) {
        try {
            String newName = getUniqueName(file);
            Media media = new Media(newName);
            Image image = storeFile(file, newName);
            image.createThumbnail(thumbnailWidth, thumbnailHeight);
            media.setAuthor(user);
            media.setKey(key);
            mediaRepository.save(media);
            return media;
        } catch (ImageException e) {
            throw new MediaUploadException("Failed to store temp file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public void attachMediasToPostByKey(Post post, Integer key) {
        Set<Media> medias = mediaRepository.findAllByKey(key);
        medias.forEach(m -> {
            Path file = Paths.get(m.getFilename());
            Path thumbnail = Paths.get("thumbnail-" + m.getFilename());
            try {
                Files.move(tempLocation.resolve(file), uploadsLocation.resolve(file));
                Files.move(tempLocation.resolve(thumbnail), uploadsLocation.resolve(thumbnail));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        mediaRepository.attachMediasToPostByKey(post, key);
    }

    @Override
    public Resource getOneResourceByFilename(String filename) {
        try {
            Path path = uploadsLocation.resolve(filename);
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new MediaUploadException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new MediaUploadException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void init() {
        try {
            if (! Files.exists(uploadsLocation)) Files.createDirectories(uploadsLocation);
        } catch (IOException e) {
            throw new MediaUploadException("Could not initialize storage", e);
        }
    }

    private Image storeFile(MultipartFile file, String newName) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        if (file.isEmpty()) throw new MediaUploadException("Failed to store empty file " + filename);
        if (filename.contains(".."))
            throw new MediaUploadException("Cannot store file with relative path outside current directory " + filename);
        try {
            Files.copy(
                    file.getInputStream(),
                    this.uploadsLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING
            );
            return image.set(getOneResourceByFilename(filename)).toJpg().rename(newName);
        } catch (Exception e) {
            throw new MediaUploadException("Could not store file: " + filename, e);
        }
    }

    private String getUniqueName(MultipartFile file) {
        return UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
    }
}
