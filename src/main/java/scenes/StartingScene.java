package main.java.scenes;

import bagel.Input;
import bagel.Keys;
import main.java.ShadowDance;
import main.java.scenes.game.RenderingScene;
import main.java.utils.TextUtils;

public class StartingScene implements RenderingScene {
    private final ShadowDance SHADOW_DANCE;

    public StartingScene(ShadowDance mainInstance) {
        this.SHADOW_DANCE = mainInstance;
    }

    @Override
    public void render(Input input) {
        if (input.wasPressed(Keys.NUM_1)) SHADOW_DANCE.setState(GameState.LEVEL_ONE);
        else if (input.wasPressed(Keys.NUM_2)) SHADOW_DANCE.setState(GameState.LEVEL_TWO);
        else if (input.wasPressed(Keys.NUM_3)) SHADOW_DANCE.setState(GameState.LEVEL_THREE);

        TextUtils.drawText(64, "SHADOW DANCE", 220, 250);
        TextUtils.drawText(24, "SELECT LEVELS WITH", 320, 440);
        TextUtils.drawText(24, "NUMBER KEYS", 320, 470);
        TextUtils.drawText(24, "    1       2       3   ", 320, 500);
    }

    @Override
    public GameState getState() {
        return GameState.START;
    }
}
