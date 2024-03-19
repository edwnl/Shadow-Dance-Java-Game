package main.java.scenes.game.levels;

import main.java.ShadowDance;
import main.java.scenes.GameState;
import main.java.scenes.game.GameScene;

public class LevelOneScene extends GameScene {
    public LevelOneScene(ShadowDance mainInstance) {
        super(mainInstance);
    }

    @Override
    public String getCSVLevelPath() {
        return "res/test1.csv";
    }

    @Override
    public int getWinningScore() {
        return 150;
    }

    @Override
    public GameState getState() {
        return GameState.LEVEL_ONE;
    }
}
