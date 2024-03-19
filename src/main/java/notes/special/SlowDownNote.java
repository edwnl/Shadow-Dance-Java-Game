package main.java.notes.special;

import main.java.lane.Lane;
import main.java.lane.LaneType;
import main.java.notes.NoteType;
import main.java.notes.AbstractNote;
import main.java.scenes.game.GameScene;

public class SlowDownNote extends AbstractNote implements SpecialNote {
    public SlowDownNote(GameScene scene, LaneType laneType, int frameNumber, NoteType noteType) {
        super(scene, laneType, frameNumber, noteType);
    }

    @Override
    public String getImagePath() {
        return "res/noteSlowDown.png";
    }

    @Override
    public String getTempMessage(int distance) {
        return "SLOW DOWN";
    }

    @Override
    public void handleKeyPress(Lane lane) {
        setNoteDescendRate(getNoteDescendRate() - 1);
    }
}
