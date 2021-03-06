package net.ignaszak.socialnetwork.service.media;

import net.ignaszak.socialnetwork.domain.Media;
import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.MediaUploadException;
import net.ignaszak.socialnetwork.model.image.Image;
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
        return mediaRepository.findAllByAuthorAndKeyIsNullOrderByCreatedDate(user, page);
    }

    @Override
    public Media saveProfileImageWithUser(MultipartFile file, User user) throws MediaUploadException {
        try {
            String newName = getUniqueJpgName();
            Media media = new Media(newName);
            Image image = storeFile(file, newName, uploadsLocation);
            image.createProfileThumbnail(profileThumbnailWidth, profileThumbnailHeight);
            user.setProfile(newName);
            media.setAuthor(user);
            mediaRepository.save(media);
            return media;
        } catch (Exception e) {
            throw new MediaUploadException("Could not save profile image: " + file.getName(), e);
        }
    }

    @Override
    public Media saveTempImageWithUserAndKey(MultipartFile file, User user, String key) throws MediaUploadException {
        try {
            String newName = getUniqueJpgName();
            Image image = storeFile(file, newName, tempLocation);
            image.createThumbnail(thumbnailWidth, thumbnailHeight);
            Media media = new Media(newName);
            media.setAuthor(user);
            media.setKey(key);
            if (image.toFile().exists()) mediaRepository.save(media);
            return media;
        } catch (Exception e) {
            throw new MediaUploadException("Could not save temp image: " + file.getName(), e);
        }
    }

    @Override
    public Set<Media> movePostMediasFromTemp(Post post) {
        Set<Media> medias = mediaRepository.findAllByKey(post.getKey());
        if (! medias.isEmpty()) {
            medias.forEach(media -> {
                String thumbnail = "thumbnail-" + media.getFilename();
                Path fileLocation = tempLocation.resolve(media.getFilename());
                Path thumbnailLocation = tempLocation.resolve(thumbnail);
                if (Files.exists(fileLocation) && Files.exists(thumbnailLocation)) {
                    try {
                        Files.move(fileLocation, uploadsLocation.resolve(media.getFilename()));
                        Files.move(thumbnailLocation, uploadsLocation.resolve(thumbnail));
                        media.setPost(post);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    mediaRepository.delete(media);
                }
            });
        }

        return medias;
    }

    @Override
    public Resource getOneResourceByFilename(String filename) throws MediaUploadException {
        Path path = uploadsLocation.resolve(filename);
        return getOneResourceByPath(path);
    }

    @Override
    public Resource getOneResourceByPath(Path path) throws MediaUploadException {
        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new MediaUploadException("Could not read file: " + path.getFileName());
            }
        } catch (MalformedURLException e) {
            throw new MediaUploadException("Could not read file: " + path.getFileName(), e);
        }
    }

    @Override
    public void init() throws MediaUploadException {
        try {
            if (! Files.exists(uploadsLocation)) Files.createDirectories(uploadsLocation);
            if (! Files.exists(tempLocation)) Files.createDirectories(tempLocation);
        } catch (IOException e) {
            throw new MediaUploadException("Could not initialize storage", e);
        }
    }

    @Override
    public Set<Media> getByPostId(Integer postId) {
        return mediaRepository.findAllByPost_IdOrderById(postId);
    }

    @Override
    public void deleteByIdAndAuthor(Integer id, User author) {
        mediaRepository.removeDistinctByIdAndAuthor(id, author);
    }

    private Image storeFile(MultipartFile file, String newName, Path path) throws Exception {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        if (file.isEmpty()) throw new MediaUploadException("Failed to store empty file " + filename);
        Path p = path.resolve(filename);
        Files.copy(file.getInputStream(), p, StandardCopyOption.REPLACE_EXISTING);
        return image.set(getOneResourceByPath(p)).toJpg().rename(newName);
    }

    private String getUniqueJpgName() {
        return UUID.randomUUID().toString() + ".jpg";
    }
}
