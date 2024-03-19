package main.java.utils;

import bagel.Image;

import java.util.HashMap;

/**
 * Caches the images so that they are only created once.
 * {@link #getImage(String)}
 */
public class ImageCacher {
    private static final HashMap<String, Image> CACHE_IMAGES = new HashMap<>();

    /**
     * @param path File Path.
     * @return cached image, or creates a new one if there is no cache yet.
     */
    public static Image getImage(String path) {
        if (!CACHE_IMAGES.containsKey(path)) {
            CACHE_IMAGES.put(path, new Image(path));
        }
        return CACHE_IMAGES.get(path);
    }
}
