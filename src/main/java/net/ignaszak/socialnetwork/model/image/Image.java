package net.ignaszak.socialnetwork.model.image;

import org.springframework.core.io.Resource;

import java.io.File;

public interface Image {

    Image set(File file);
    Image set(Resource resource);
    Image toJpg() throws ImageException;
    Image rename(String newName) throws ImageException;
    Image createThumbnail(int width, int height) throws ImageException;
    Image createProfileThumbnail(int width, int height) throws ImageException;
    File toFile();
    boolean isJpg(File file);
}
