package main.java.scenes.game;

import bagel.Input;
import main.java.lane.Lane;
import main.java.notes.AbstractNote;
import main.java.ShadowDance;
import main.java.utils.Accuracy;
import main.java.lane.LaneType;
import main.java.scenes.GameState;
import main.java.notes.NoteType;
import main.java.utils.TextUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GameScene implements RenderingScene {
    // Hashmap keeping track of all the lanes.
    private final HashMap<LaneType, Lane> LANES = new HashMap<>();
    private final ShadowDance SHADOW_DANCE;
    private final TemporaryMessageManager MESSAGE_MANAGER;

    // Keeps track of frame number.
    private int FRAME_NUM = 0;

    // Player score.
    public static int scoreMultiplier = 1;
    private int scoreMultiplierExpireFrame, score = 0;
    private static int SCORE_MULTI_DURATION = 480;

    public GameScene(ShadowDance mainInstance) {
        this.SHADOW_DANCE = mainInstance;
        this.MESSAGE_MANAGER = new TemporaryMessageManager(this);
    }

    public void setupLevel() {
        // Clear previous level data
        LANES.clear();
        FRAME_NUM = 0;
        MESSAGE_MANAGER.clear();
        // Reset multipliers
        scoreMultiplier = 1;
        AbstractNote.noteDescendRate = AbstractNote.DEFAULT_DESCEND_RATE;
        scoreMultiplierExpireFrame = 0;
        // Reset Score
        score = 0;
        // Re-load the level
        loadLevel();
    }

    public void loadLevel() {
        // Reads a CSV file, and populates the game scene with variables.
        // CSV Structure:
        // Lane, type of lane, x-coordinate (or)
        // Type of lane, type of note, frame-number
        try (BufferedReader reader = new BufferedReader(new FileReader(getCSVLevelPath()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Reading CSV by splitting the commas
                String[] parts = line.split(",");
                if (parts.length < 3) return;

                String field1 = parts[0].trim(),
                        field2 = parts[1].trim(),
                        field3 = parts[2].trim();

                // Registering Lanes
                if (field1.equals("Lane")) {
                    registerLane(
                            LaneType.valueOf(field2),
                            Integer.parseInt(field3)
                    );
                    // Registering Notes
                } else {
                    registerNote(this,
                            LaneType.valueOf(field1),
                            Integer.parseInt(field3),
                            NoteType.valueOf(field2)
                    );
                }
            }
            // IllegalArgumentException thrown if the fields don't match enum.
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Input input) {
        long start = System.currentTimeMillis();
        // When the game scene is rendered, increment FRAME_NUM.
        FRAME_NUM++;

        // Decide if a game should finish based on if main.java.notes
        // are still being rendered.
        boolean gameInProgress = false;

        // Render every lane.
        for (Lane lane: LANES.values()) {
            // renderLaneAndNotes returns true if any note is rendered.
            if (lane.renderLaneAndNotes(input)) gameInProgress = true;
        }

        // Ending the game by updating the state.
        if (!gameInProgress) SHADOW_DANCE.showEndScreen(this);

        MESSAGE_MANAGER.render();

        // Drawing the score.
        TextUtils.drawText(30, "SCORE " + score, 35, 35);
        // Debug Text.
        TextUtils.drawText(15, "SCORE MULTIPLIER " + scoreMultiplier, 35, 50);
        TextUtils.drawText(15, "DESCEND RATE " + AbstractNote.getNoteDescendRate(), 35, 65);
        TextUtils.drawText(15, "FRAME NUM " + FRAME_NUM, 35, 80);
    }

    public void addScore(int amt) {
        if (getFrameNumber() > scoreMultiplierExpireFrame) {
            scoreMultiplier = 1;
        }
        score += amt * scoreMultiplier;
    }

    public void handleAccuracy(Accuracy accuracy) {
        if (accuracy == null) return;
        // Modify the player score
        addScore(accuracy.getScoreValue());
        MESSAGE_MANAGER.sendMessage(accuracy);
    }

    /**
     * @return a list containing the valid main.java.notes from all lanes.
     */
    public List<AbstractNote> getAllNotes() {
        return LANES.values()
                .stream()
                .flatMap(lane -> lane.getValidNotes().stream())
                .collect(Collectors.toList());
    }

    public void registerLane(LaneType laneType, int x_coord) {
        laneType.setXCoord(x_coord);
        LANES.put(laneType, new Lane(laneType, this));
    }

    /**
     * Dynamically create an instance of AbstractNote, based on the NoteType.
     * @param scene GameScene reference
     * @param type Type of the lane.
     * @param frameNumber Spawning Frame Number.
     * @param noteType Type of the note.
     */
    public void registerNote(GameScene scene, LaneType type, int frameNumber, NoteType noteType) {
        try {
            LANES.get(type).addNote(noteType
                    .getNoteClass()
                    .getDeclaredConstructor(
                            GameScene.class,
                            LaneType.class,
                            int.class,
                            NoteType.class
                    )
                    .newInstance(scene, type, frameNumber, noteType));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Lane getLane(LaneType dir) {
        if (!LANES.containsKey(dir)) throw new Error("Lane Missing for Direction: " + dir);
        return LANES.get(dir);
    }

    public void doubleScoreMultiplier() {
        this.scoreMultiplier *= 2;
        this.scoreMultiplierExpireFrame = FRAME_NUM + SCORE_MULTI_DURATION;
    }

    public int getScore() {
        return score;
    }

    public abstract int getWinningScore();

    public abstract String getCSVLevelPath();

    public abstract GameState getState();

    public int getFrameNumber() {
        return FRAME_NUM;
    }

    public TemporaryMessageManager messageManager() {
        return MESSAGE_MANAGER;
    }
}
