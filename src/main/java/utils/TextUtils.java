package main.java.utils;

import bagel.Font;
import bagel.Window;

import java.util.HashMap;

/**
 * Utility class to draw texts.
 * @author Edwin Li
 */
public class TextUtils {
    // Caches the fonts, so they are not created over and over again.
    private final static HashMap<Integer, Font> fonts = new HashMap<>();
    // Path to the font file.
    private final static String FONT_PATH = "res/FSO8BITR.ttf";

    /**
     * Draws text on screen.
     * @param size Font Size.
     * @param text Text to Draw.
     * @param x X coordinate of the text.
     * @param y Y coordinate of the text.
     */
    public static void drawText(int size, String text, int x, int y) {
        fonts.putIfAbsent(size, new Font(FONT_PATH, size));
        Font font = fonts.get(size);
        font.drawString(text, x, y);
    }

    /**
     * Draws text in the center of the screen.
     * @param size Font Size.
     * @param text Text to Draw.
     */
    public static void drawTextCentered(int size, String text) {
        drawTextCentered(size, -1, text);
    }

    /**
     * Draws text in the center of the screen.
     * @param size Font Size.
     * @param text Text to Draw.
     * @param yOverwrite Overwrites the y coordinate. Pass -1 to ignore.
     */
    public static void drawTextCentered(int size, int yOverwrite, String text) {
        fonts.putIfAbsent(size, new Font(FONT_PATH, size));
        Font font = fonts.get(size);

        // Calculate the x and y coordinates to center the text on the screen
        double textWidth = font.getWidth(text);
        int x = (int) (Window.getWidth() - textWidth) / 2;  // Center horizontally
        int y = Window.getHeight() / 2;  // Center vertically

        if (yOverwrite != -1) y = yOverwrite; // Y overwrite

        font.drawString(text, x, y);
    }
}
