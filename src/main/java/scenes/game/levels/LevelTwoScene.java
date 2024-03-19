package main.java.scenes.game.levels;

import main.java.ShadowDance;
import main.java.scenes.GameState;
import main.java.scenes.game.GameScene;

public class LevelTwoScene extends GameScene {

    public LevelTwoScene(ShadowDance mainInstance) {
        super(mainInstance);
    }

    @Override
    public String getCSVLevelPath() {
        return "res/test2.csv";
    }

    @Override
    public int getWinningScore() {
        return 400;
    }

    @Override
    public GameState getState() {
        return GameState.LEVEL_TWO;
    }
}
