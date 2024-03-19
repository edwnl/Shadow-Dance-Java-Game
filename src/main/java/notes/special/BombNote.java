package main.java.notes.special;

import main.java.lane.Lane;
import main.java.lane.LaneType;
import main.java.notes.NoteType;
import main.java.notes.AbstractNote;
import main.java.scenes.game.GameScene;

public class BombNote extends AbstractNote implements SpecialNote {
    public BombNote(GameScene scene, LaneType laneType, int frameNumber, NoteType noteType) {
        super(scene, laneType, frameNumber, noteType);
    }

    @Override
    public String getImagePath() {
        return "res/noteBomb.png";
    }

    @Override
    public String getTempMessage(int distance) {
        return "BOOM!";
    }

    @Override
    public void handleKeyPress(Lane lane) {
        lane.bombNotes();
    }
}
