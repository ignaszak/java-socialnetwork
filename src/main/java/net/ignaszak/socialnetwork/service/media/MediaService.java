package net.ignaszak.socialnetwork.service.media;

import net.ignaszak.socialnetwork.domain.Media;
import net.ignaszak.socialnetwork.domain.User;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {

    Media saveProfileImageWithUser(MultipartFile file, User user);
    Media saveImageWithUser(MultipartFile file, User user);
    Resource getOneResourceByFilename(String filename);
    Page<Media> getByUser(User user, Pageable page);
    void init();
}
