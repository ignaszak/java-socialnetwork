package net.ignaszak.socialnetwork.service.media;

import net.ignaszak.socialnetwork.domain.Media;
import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.MediaUploadException;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Set;

public interface MediaService {

    Media saveProfileImageWithUser(MultipartFile file, User user) throws MediaUploadException;
    Media saveTempImageWithUserAndKey(MultipartFile file, User user, String key) throws MediaUploadException;
    Set<Media> movePostMediasFromTemp(Post post);
    Resource getOneResourceByFilename(String filename) throws MediaUploadException;
    Resource getOneResourceByPath(Path path) throws MediaUploadException;
    Page<Media> getByUser(User user, Pageable page);
    void init() throws MediaUploadException;
    Set<Media> getByPostId(Integer postId);
    void deleteByIdAndAuthor(Integer id, User author);
}
