package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.Media;
import net.ignaszak.socialnetwork.dto.SuccessRestDTO;
import net.ignaszak.socialnetwork.exception.MediaUploadException;
import net.ignaszak.socialnetwork.service.media.MediaService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("rest-api/posts")
public class PostsMediasRestController {

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

    @PostMapping(value = "/medias/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Media uploadImage(
        @PathVariable("key") String key,
        @RequestParam("image") MultipartFile image
    ) throws MediaUploadException {
        return mediaService.saveTempImageWithUserAndKey(image, userService.getCurrentUser(), key);
    }

    @DeleteMapping(value = "/medias/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessRestDTO deleteMedia(@PathVariable("id") Integer id) {
        mediaService.deleteByIdAndAuthor(id, userService.getCurrentUser());
        return new SuccessRestDTO();
    }
}
