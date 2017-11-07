package net.ignaszak.socialnetwork.model.image;

import net.coobird.thumbnailator.ThumbnailParameter;

public abstract class Rename extends net.coobird.thumbnailator.name.Rename {

    public static final Rename PREFIX_HYPHEN_THUMBNAIL = new Rename() {
        @Override
        public String apply(String fileName, ThumbnailParameter param)
        {
            return appendPrefix(fileName, "thumbnail-");
        }
    };

    public static final Rename PREFIX_HYPHEN_PROFILE_THUMBNAIL = new Rename() {
        @Override
        public String apply(String fileName, ThumbnailParameter param)
        {
            return appendPrefix(fileName, "profile-thumbnail-");
        }
    };
}
