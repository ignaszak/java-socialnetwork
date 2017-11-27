package net.ignaszak.socialnetwork.service.media;

import net.ignaszak.socialnetwork.domain.Media;
import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.domain.User;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Set;

public interface MediaService {

    Media saveProfileImageWithUser(MultipartFile file, User user) throws Exception;
    Media saveTempImageWithUserAndKey(MultipartFile file, User user, String key) throws Exception;
    Set<Media> movePostMediasFromTemp(Post post);
    Resource getOneResourceByFilename(String filename);
    Resource getOneResourceByPath(Path path);
    Page<Media> getByUser(User user, Pageable page);
    void init();
    Set<Media> getByPostId(Integer postId);
}
