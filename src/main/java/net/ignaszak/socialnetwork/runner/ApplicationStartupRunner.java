package net.ignaszak.socialnetwork.runner;

import net.ignaszak.socialnetwork.service.media.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {

    private MediaService mediaService;

    @Autowired
    public void setMediaService(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @Override
    public void run(String... args) throws Exception {
        mediaService.init();
    }
}
