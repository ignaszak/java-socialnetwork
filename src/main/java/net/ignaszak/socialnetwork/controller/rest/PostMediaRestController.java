package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.Media;
import net.ignaszak.socialnetwork.service.media.MediaService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("rest-api/posts")
public class PostMediaRestController {

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

    @GetMapping(value = "/{id}/medias", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Media> get(@PathVariable("id") Integer id) {
        return mediaService.getByPostId(id);
    }

    @PostMapping(value = "/medias/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Media uploadImage(@PathVariable("key") String key, @RequestParam("image") MultipartFile image) {
        return mediaService.saveTempImageWithUserAndKey(image, userService.getCurrentUser(), key);
    }
}
