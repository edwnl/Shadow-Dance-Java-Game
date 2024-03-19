package main.java.lane;

import bagel.Image;
import bagel.Input;
import bagel.Keys;
import main.java.notes.AbstractNote;
import main.java.scenes.game.GameScene;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Lanes of the Shadow Dance game.
 */
public class Lane {
    // Notes within the lane.
    private final ArrayList<AbstractNote> LANE_NOTES = new ArrayList<>();
    private final GameScene SCENE;
    private final LaneType LANE_TYPE;
    private final Image LANE_IMAGE;

    // The Y coordinate of the stationary target main.java.notes.
    public static final int NOTE_TARGET_YCOORD = 657;
    // The X & Y coordinate of where to draw the lane.
    protected static final int LANE_YCOORD = 384;
    private static final int NOTE_DETECTION_RAGE = 200;
    public final int LANE_XCOORD;

    // The next note which will be processed when a key is pressed.
    private AbstractNote lowestValidNote;

    public Lane(LaneType dir, GameScene scene){
        this.LANE_TYPE = dir;
        this.LANE_XCOORD = dir.getXCoord();
        this.LANE_IMAGE = dir.getLaneImage();
        this.SCENE = scene;
    }

    /**
     * Renders the lanes and their main.java.notes.
     * @return True if main.java.notes were drawn.
     */
    public boolean renderLaneAndNotes(Input input) {
        // Draw the lane.
        LANE_IMAGE.draw(LANE_XCOORD, LANE_YCOORD);
        boolean drawnNotes = false;

        // Handle keyboard presses for each lane
        Keys key = getDirection().getKEY();
        boolean pressed = input.wasPressed(key);
        boolean released = input.wasReleased(key);

        if ((pressed || released) && lowestValidNote != null) {
            lowestValidNote.clickReleaseHandler(released, false).accept(this);
        }

        // Draw the main.java.notes within the lane.
        for (AbstractNote note : LANE_NOTES) {
            // Check if the main.java.notes are destroyed.
            if (note.isDestroyed()) continue;

            // Draw the note.
            note.drawNote(this);
            drawnNotes = true;

            int y = note.getNoteYCoord();
            // If the note is within 200px from the target
            if (y < NOTE_TARGET_YCOORD && y > NOTE_TARGET_YCOORD - NOTE_DETECTION_RAGE) {
                // Update the lowest valid note
                if (lowestValidNote == null || y > lowestValidNote.getNoteYCoord()) {
                    lowestValidNote = note;
                }
            }
        }

        return drawnNotes;
    }

    public void addNote(AbstractNote note) {
        LANE_NOTES.add(note);
    }

    public LaneType getDirection() {
        return LANE_TYPE;
    }

    /**
     * @return all spawned main.java.notes which are not destroyed.
     */
    public List<AbstractNote> getValidNotes() {
        return LANE_NOTES
                .stream()
                .filter(AbstractNote::isOnScreen)
                .collect(Collectors.toList());
    }

    /**
     * Bomb notes by removing all notes which are on screen.
     */
    public void bombNotes() {
        LANE_NOTES.stream()
                .filter(AbstractNote::isOnScreen)
                .forEach(AbstractNote::destroy);
    }

    public void destroyLowestNote() {
        lowestValidNote.destroy();
        lowestValidNote = null;
    }

    public GameScene getSCENE() {
        return SCENE;
    }
}
