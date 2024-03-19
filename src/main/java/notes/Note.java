package main.java.notes;

import main.java.lane.LaneType;
import main.java.scenes.game.GameScene;

public class Note extends AbstractNote {
    public Note(GameScene scene, LaneType laneType, int frameNumber, NoteType noteType) {
        super(scene, laneType, frameNumber, noteType);
    }

    @Override
    public String getImagePath() {
        return "res/note" + getLANE_TYPE() + ".png";
    }
}
