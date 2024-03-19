package main.java.notes;

import bagel.Image;
import bagel.Window;
import main.java.utils.Accuracy;
import main.java.lane.Lane;
import main.java.lane.LaneType;
import main.java.notes.special.SpecialNote;
import main.java.scenes.game.GameScene;
import main.java.utils.ImageCacher;
import org.lwjgl.opengl.ARBCullDistance;

import java.util.function.Consumer;

/**
 * Notes of the Shadow Dance game.
 */
public abstract class AbstractNote {
    // References to the scene, and lane.
    private final GameScene SCENE;
    private final Lane LANE;

    // Note Properties.
    private final NoteType TYPE;
    private final LaneType LANE_TYPE;
    private final int NOTE_START_YCOORD;
    private final int NOTE_SPAWN_FRAME;
    private final int NOTE_XCOORD;

    // Amount of pixels the note descends per frame.
    public static int noteDescendRate = 2;
    public static int DEFAULT_DESCEND_RATE = 2;

    private int noteYCoord = 0; // Current Y Location of the note.
    private boolean destroyed; // Keeps track if the note is destroyed.

    public AbstractNote(GameScene scene, LaneType laneType, int frameNumber, NoteType noteType) {
        this.SCENE = scene;
        this.LANE_TYPE = laneType;
        this.TYPE = noteType;
        this.NOTE_SPAWN_FRAME = frameNumber;
        this.LANE = scene.getLane(laneType);
        NOTE_XCOORD = LANE.LANE_XCOORD;

        // Getting the correct note starting y coordinate.
        NOTE_START_YCOORD = getYStartCoord();
    }

    /**
     * Handles the removal and scoring of a note when a key is pressed.
     * @param released Set to true if it was a release event.
     */
    public Consumer<Lane> clickReleaseHandler(boolean released, boolean missed) {
        return lane -> {
            int yCoord = getNoteYCoord();
            NoteType type = getNoteType();
            // Ignore the release event if the note is not of HOLD type.
            // Ignore key press if there is no lowest valid note
            if (this.isDestroyed()) return;
            if (released && type != NoteType.Hold && !missed) return;
            if (this instanceof HoldNote) {
                if (!((HoldNote) this).isValidPress(released)) return;;
            }

            // Remove the note
            if (type != NoteType.Hold || released || missed) {
                lane.destroyLowestNote();
            }

            if (missed) {
                SCENE.addScore(Accuracy.MISS.getScoreValue());
                if (!(this instanceof SpecialNote)) {
                    SCENE.messageManager().sendMessage(Accuracy.MISS);
                }
                return;
            }

            // Hold Start: +82 to Center Coordinate, Release: -82 to Center Coordinate
            if (type.equals(NoteType.Hold)) yCoord += released
                    ? -HoldNote.HALF_LENGTH : HoldNote.HALF_LENGTH;
            int distance = Math.abs(Lane.NOTE_TARGET_YCOORD - yCoord);

            Accuracy accuracy = Accuracy.getAccuracyFromDistance(distance);
            SCENE.addScore(accuracy.getScoreValue());

            String msg = getTempMessage(distance);
            SCENE.messageManager().sendMessage(msg);

            if (this instanceof SpecialNote) {
                ((SpecialNote) this).handleKeyPress(lane);
            }
        };
    }

    public void drawNote(Lane lane) {
        // Find the current Y coordinate, based on the frame number.
        noteYCoord = calcNoteYCoord();
        if (noteYCoord < 0) return;

        // Disappeared off the bottom of the screen
        if (noteYCoord > Window.getHeight()) {
            clickReleaseHandler(true, true).accept(lane);
            return;
        }

        // Draw the note
        getNoteImage().draw(NOTE_XCOORD, noteYCoord);
    }

    private int calcNoteYCoord() {
        // Note not spawned yet
        if (SCENE.getFrameNumber() < NOTE_SPAWN_FRAME) return -1;

        return noteYCoord + noteDescendRate;
//        // DESC FRAMES = DESCEND RATE * FRAMES SPAWNED
//        int desc_frames = noteDescendRate * (SCENE.getFrameNumber() - NOTE_SPAWN_FRAME);
//        return NOTE_START_YCOORD + desc_frames;
    }

    public boolean spawned() {
        return SCENE.getFrameNumber() > NOTE_SPAWN_FRAME;
    }

    public boolean isOnScreen() {
        return spawned() && !isDestroyed();
    }

    public NoteType getNoteType() {
        return TYPE;
    }

    public void destroy() {
        this.destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public int getNoteYCoord() {
        return noteYCoord;
    }

    public int getNoteXCoord() {
        return NOTE_XCOORD;
    }

    public LaneType getLANE_TYPE() {
        return LANE_TYPE;
    }

    protected int getYStartCoord() {
        return 100;
    }

    public String getTempMessage(int distance) {
        return Accuracy.getAccuracyFromDistance(distance).toString();
    }

    public abstract String getImagePath();

    private Image getNoteImage() {
        return ImageCacher.getImage(getImagePath());
    }

    public GameScene getSCENE() {
        return SCENE;
    }

    public static int getNoteDescendRate() {
        return noteDescendRate;
    }

    public static void setNoteDescendRate(int noteDescendRate) {
        AbstractNote.noteDescendRate = Math.max(1, noteDescendRate);
    }
}
