package main.java.notes.special;

import main.java.lane.Lane;
import main.java.lane.LaneType;
import main.java.notes.NoteType;
import main.java.notes.AbstractNote;
import main.java.scenes.game.GameScene;

public class DoubleScoreNote extends AbstractNote implements SpecialNote {
    public DoubleScoreNote(GameScene scene, LaneType laneType, int frameNumber, NoteType noteType) {
        super(scene, laneType, frameNumber, noteType);
    }

    @Override
    public String getImagePath() {
        return "res/note2x.png";
    }

    @Override
    public String getTempMessage(int distance) {
        return "DOUBLE SCORE";
    }

    @Override
    public void handleKeyPress(Lane lane) {
        lane.getSCENE().doubleScoreMultiplier();
    }
}
