package main.java.scenes;

import bagel.Input;
import bagel.Keys;
import main.java.ShadowDance;
import main.java.scenes.game.RenderingScene;
import main.java.utils.TextUtils;

public class EndScene implements RenderingScene {
    private boolean clear;
    private ShadowDance mainInstance;

    public EndScene(ShadowDance mainInstance) {
        this.mainInstance = mainInstance;
    }

    @Override
    public void render(Input input) {
        if (input.wasPressed(Keys.SPACE)) {
            mainInstance.setState(GameState.START);
        }

        // Draws centered text based on score.
        TextUtils.drawTextCentered(64, 300, clear
                        ? "CLEAR!"
                        : "TRY AGAIN");

        TextUtils.drawTextCentered(24, 500,
                "PRESS SPACE TO RETURN TO LEVEL SELECTION");
    }

    @Override
    public GameState getState() {
        return GameState.END;
    }

    public void setClear(boolean clear) {
        this.clear = clear;
    }
}
