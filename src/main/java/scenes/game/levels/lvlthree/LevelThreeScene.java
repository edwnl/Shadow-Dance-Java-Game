package main.java.scenes.game.levels.lvlthree;

import bagel.Image;
import bagel.Input;
import bagel.Keys;
import main.java.notes.AbstractNote;
import main.java.ShadowDance;
import main.java.scenes.GameState;
import main.java.scenes.game.GameScene;
import main.java.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class LevelThreeScene extends GameScene {
    // List of enemies and projectiles
    private final Guardian GUARDIAN;

    public LevelThreeScene(ShadowDance mainInstance) {
        super(mainInstance);
        GUARDIAN = new Guardian();
    }

    @Override
    public String getCSVLevelPath() {
        return "res/test3.csv";
    }

    @Override
    public int getWinningScore() {
        return 350;
    }

    @Override
    public void setupLevel() {
        super.setupLevel();
        GUARDIAN.reset();
    }

    @Override
    public GameState getState() {
        return GameState.LEVEL_THREE;
    }

    @Override
    public void render(Input input) {
        super.render(input);
        GUARDIAN.update(input, getFrameNumber(), getAllNotes());
    }
}
