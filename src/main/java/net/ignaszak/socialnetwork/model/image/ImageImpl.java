package net.ignaszak.socialnetwork.model.image;

import com.google.common.io.Files;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Component
public class ImageImpl implements Image {

    private File file;

    public ImageImpl() {}

    public ImageImpl(File file) {
        set(file);
    }

    public ImageImpl(Resource resource) {
        set(resource);
    }

    public Image set(File file) {
        this.file = file;
        return this;
    }

    public Image set(Resource resource) {
        try {
            file = resource.getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public Image toJpg() throws ImageException {
        try {
            check(file);
            if (isJpg(file)) return this;
            BufferedImage bufferedImage = ImageIO.read(file);
            BufferedImage newBufferedImage = new BufferedImage(
                    bufferedImage.getWidth(),
                    bufferedImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB
            );
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
            ImageIO.write(newBufferedImage, "jpg", file);
        } catch (IOException e) {
            throw new ImageException("Could not convert to jpg file: " + file.getName(), e);
        }
        return this;
    }

    @Override
    public Image rename(String newName) throws ImageException {
        String pathName = file.getParent();
        File newFile = Paths.get(pathName).resolve(newName).toFile();
        try {
            Files.move(file, newFile);
            file = newFile;
        } catch (IOException e) {
            throw new ImageException("Could not rename file: " + file.getName(), e);
        }
        return this;
    }

    @Override
    public File toFile() {
        return file;
    }

    @Override
    public Image createThumbnail(int width, int height) throws ImageException {
        createThumbnail(width, height, Rename.PREFIX_HYPHEN_THUMBNAIL, false);
        return this;
    }

    @Override
    public Image createProfileThumbnail(int width, int height) throws ImageException {
        createThumbnail(width, height, Rename.PREFIX_HYPHEN_PROFILE_THUMBNAIL, true);
        return this;
    }

    public boolean isJpg(File file) {
        String extension = Files.getFileExtension(file.getName()).toLowerCase();
        return extension.equals("jpg");
    }

    private void createThumbnail(int width, int height, Rename rename, boolean force) throws ImageException {
        try {
            Thumbnails.Builder builder = Thumbnails.of(file);
            if (force) {
                builder.forceSize(width, height);
            } else {
                builder.size(width, height);
            }
            builder.toFiles(Paths.get(file.getParent()).toFile(), rename);
        } catch (IOException e) {
            throw new ImageException("Could not create thumbnail for image: " + file.getName(), e);
        }
    }

    private void check(File file) throws IOException {
        if (! file.exists() || ! file.canRead() || ! file.canWrite())
            throw new IOException("Invalid file: " + file.getName());
    }
}
