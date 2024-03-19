package main.java;

import bagel.*;
import main.java.notes.AbstractNote;
import main.java.notes.special.BombNote;
import main.java.notes.special.SpecialNote;
import main.java.scenes.GameState;
import main.java.scenes.EndScene;
import main.java.scenes.game.GameScene;
import main.java.scenes.game.levels.LevelOneScene;
import main.java.scenes.game.levels.lvlthree.LevelThreeScene;
import main.java.scenes.game.levels.LevelTwoScene;
import main.java.scenes.game.RenderingScene;
import main.java.scenes.StartingScene;

import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2023
 * Please enter your name below
 * @author Edwin Li
 */
public class ShadowDance extends AbstractGame {
    private GameState state = GameState.START;

    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");

    // Scenes
    private RenderingScene curr_scene;
    private final ArrayList<RenderingScene> SCENES = new ArrayList<>();

    public ShadowDance() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        SCENES.add(new StartingScene(this));
        SCENES.add(new LevelOneScene(this));
        SCENES.add(new LevelTwoScene(this));
        SCENES.add(new LevelThreeScene(this));
        SCENES.add(new EndScene(this));
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)) Window.close();

        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        // Find the scene based on the state.
        curr_scene = getScene(state);
        // If a scene is found, call the render method.
        if (curr_scene != null) curr_scene.render(input);
    }

    public RenderingScene getScene(GameState state) {
        for (RenderingScene scene: SCENES) {
            if (scene.getState().equals(state)) return scene;
        }
        throw new RuntimeException("No Scene found for state: " + state);
    }

    public void setState(GameState state) {
        this.state = state;

        RenderingScene scene = getScene(state);
        if (scene instanceof GameScene) {
            GameScene gameScene = (GameScene) scene;
            gameScene.setupLevel();
        }
    }

    /**
     * Ends the game, and decides if the player has passed the target
     * score for the level. Clear / Try Again is shown appropriately.
     */
    public void showEndScreen(GameScene scene) {
        EndScene endScene = (EndScene) getScene(GameState.END);
        endScene.setClear(scene.getScore() >= scene.getWinningScore());
        this.state = GameState.END;
    }
}
