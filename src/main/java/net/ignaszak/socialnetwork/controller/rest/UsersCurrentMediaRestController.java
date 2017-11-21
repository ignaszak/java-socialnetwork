package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.Media;
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

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Media uploadImage(@RequestParam("image") MultipartFile image) {
        return mediaService.saveImageWithUser(image, userService.getCurrentUser());
    }

    @PostMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public Media uploadProfile(@RequestParam("image") MultipartFile image) {
        return mediaService.saveProfileImageWithUser(image, userService.getCurrentUser());
    }
}
