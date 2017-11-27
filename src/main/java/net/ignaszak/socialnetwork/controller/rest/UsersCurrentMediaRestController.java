package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.Media;
import net.ignaszak.socialnetwork.exception.MediaUploadException;
import net.ignaszak.socialnetwork.service.media.MediaService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("rest-api/users/current/medias")
public class UsersCurrentMediaRestController {

    private MediaService mediaService;
    private UserService userService;

    @Autowired
    public void setMediaService(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Media> findAll(Pageable page) {
        return mediaService.getByUser(userService.getCurrentUser(), page);
    }

    @PostMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public Media uploadProfile(@RequestParam("image") MultipartFile image) {
        try {
            return mediaService.saveProfileImageWithUser(image, userService.getCurrentUser());
        } catch (Exception e) {
            throw new MediaUploadException("Failed to upload file: " + image.getOriginalFilename(), e);
        }
    }
}
