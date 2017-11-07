package net.ignaszak.socialnetwork.service.media;

import net.ignaszak.socialnetwork.domain.Media;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.MediaUploadException;
import net.ignaszak.socialnetwork.model.image.Image;
import net.ignaszak.socialnetwork.model.image.ImageException;
import net.ignaszak.socialnetwork.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class MediaServiceImpl implements MediaService {

    private MediaRepository mediaRepository;
    private Image image;
    private Path uploadsLocation;
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
            Media media = new Media();
            Image image = storeFile(file, media.getUuid());
            image.createProfileThumbnail(profileThumbnailWidth, profileThumbnailHeight);
            user.setProfile(image.toFile().getName());
            media.setAuthor(user);
            mediaRepository.save(media);
            return media;
        } catch (ImageException e) {
            throw new MediaUploadException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Media saveImageWithUser(MultipartFile file, User user) {
        try {
            Media media = new Media();
            Image image = storeFile(file, media.getUuid());
            image.createThumbnail(thumbnailWidth, thumbnailHeight);
            media.setAuthor(user);
            mediaRepository.save(media);
            return media;
        } catch (ImageException e) {
            throw new MediaUploadException("Failed to store file " + file.getOriginalFilename(), e);
        }
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

    private Image storeFile(MultipartFile file, String newNameWithoutExtension) {
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
            return image.set(getOneResourceByFilename(filename)).toJpg().rename(newNameWithoutExtension + ".jpg");
        } catch (Exception e) {
            throw new MediaUploadException("Could not store file: " + filename, e);
        }
    }
}
