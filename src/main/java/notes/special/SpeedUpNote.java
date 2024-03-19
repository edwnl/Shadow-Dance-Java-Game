package main.java.notes.special;

import main.java.lane.Lane;
import main.java.lane.LaneType;
import main.java.notes.NoteType;
import main.java.notes.AbstractNote;
import main.java.scenes.game.GameScene;

public class SpeedUpNote extends AbstractNote implements SpecialNote {
    public SpeedUpNote(GameScene scene, LaneType laneType, int frameNumber, NoteType noteType) {
        super(scene, laneType, frameNumber, noteType);
    }

    @Override
    public String getImagePath() {
        return "res/noteSpeedUp.png";
    }

    @Override
    public void handleKeyPress(Lane lane) {
        setNoteDescendRate(getNoteDescendRate() + 1);
    }

    @Override
    public String getTempMessage(int distance) {
        return "SPEED UP";
    }
}
