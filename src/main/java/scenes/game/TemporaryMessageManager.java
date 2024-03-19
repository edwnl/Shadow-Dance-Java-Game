package main.java.scenes.game;

import main.java.utils.Accuracy;
import main.java.utils.TextUtils;

/**
 * Handles rendering of the temporary message on screen.
 */
public class TemporaryMessageManager {
    private static final int RENDER_TIME = 30;
    // To display the accuracy text on screen
    private String message;
    private int accuracyStringRemoveFrame;
    private final GameScene SCENE;

    public TemporaryMessageManager(GameScene scene) {
        this.SCENE = scene;
    }

    public void clear() {
        message = null;
    }

    public void render() {
        if (message != null) {
            // Draw the text only if the frame number before
            // the remove one.
            if (SCENE.getFrameNumber() < accuracyStringRemoveFrame){
                TextUtils.drawTextCentered(40, message);
            }
            // Set accuracy to null once the removal frame is past.
            else message = null;
        }
    }

    /**
     * Sends a message for 40 frames.
     * @param message Message to display.
     */
    public void sendMessage(String message) {
        this.message = message;
        accuracyStringRemoveFrame = SCENE.getFrameNumber() + RENDER_TIME;
    }

    /**
     * Sends a message for 40 frames.
     * @param accuracy Accuracy to display. (Converted to string)
     */
    public void sendMessage(Accuracy accuracy) {
        sendMessage(accuracy.toString());
    }
}
