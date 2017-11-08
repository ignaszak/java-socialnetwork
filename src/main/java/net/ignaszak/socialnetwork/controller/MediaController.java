package net.ignaszak.socialnetwork.controller;

import com.google.common.io.ByteStreams;
import net.ignaszak.socialnetwork.exception.ResourceNotFoundException;
import net.ignaszak.socialnetwork.service.media.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("public/medias")
public class MediaController {

    private MediaService mediaService;

    @Autowired
    public void setMediaService(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @ResponseBody
    @GetMapping(value = "/{filename:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> get(@PathVariable String filename) {
        try {
            Resource resource = mediaService.getOneResourceByFilename(filename);
            return new ResponseEntity<>(
                    ByteStreams.toByteArray(resource.getInputStream()), new HttpHeaders(), HttpStatus.CREATED
            );
        } catch (Exception e) {
            throw new ResourceNotFoundException("File '" + filename + "' does not exist!", e);
        }
    }
}
