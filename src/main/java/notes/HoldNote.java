package main.java.notes;

import main.java.lane.LaneType;
import main.java.scenes.game.GameScene;

public class HoldNote extends AbstractNote {
    public static final int HALF_LENGTH = 82;
    private boolean wasPressed = false;

    public HoldNote(GameScene scene, LaneType laneType, int frameNumber, NoteType noteType) {
        super(scene, laneType, frameNumber, noteType);
    }

    public boolean isValidPress(boolean released) {
        // Note released but was not pressed before.
        // This is due to two notes being very close. Ignore
        if (released && !wasPressed) return false;

        // If pressed, updated.
        if (!released) wasPressed = true;
        return true;
    }

    @Override
    protected int getYStartCoord() {
        return 24;
    }

    @Override
    public String getImagePath() {
        return "res/holdNote" + getLANE_TYPE() + ".png";
    }
}
