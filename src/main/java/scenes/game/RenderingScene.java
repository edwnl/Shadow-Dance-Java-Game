package main.java.scenes.game;

import bagel.Input;
import main.java.scenes.GameState;

/**
 * The interface documenting what each scene can do.
 * The render function is called per frame when the scene is live.
 */
public interface RenderingScene {
    /**
     * Called right after {@link bagel.AbstractGame}'s update method.
     * @param input User inputs {@link Input}.
     */
    void render(Input input);
    GameState getState();
}
