package main.java.notes;

import bagel.Image;
import main.java.lane.LaneType;
import main.java.notes.special.*;

import java.util.HashMap;

/**
 * Describes if a note require pressing once (Normal),
 * or holding (Hold).
 */
public enum NoteType {
    Normal(Note.class),
    Hold(HoldNote.class),
    DoubleScore(DoubleScoreNote.class),
    SpeedUp(SpeedUpNote.class),
    SlowDown(SlowDownNote.class),
    Bomb(BombNote.class);

    // Used to dynamically instantiate a note based on type read.
    // The type must match the Enum constant.
    final Class<? extends AbstractNote> noteClass;

    NoteType(Class<? extends AbstractNote> noteClass) {
        this.noteClass = noteClass;
    }

    public Class<? extends AbstractNote> getNoteClass() {
        return noteClass;
    }

    /**
     * Returns true if the note is one of the 4 special note types.
     */
    public boolean isSpecial() {
        return SpecialNote.class.isAssignableFrom(getNoteClass());
    }
}
