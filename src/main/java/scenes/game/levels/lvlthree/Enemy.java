package main.java.scenes.game.levels.lvlthree;

import bagel.Image;
import main.java.notes.AbstractNote;
import main.java.utils.ImageCacher;
import main.java.utils.Utils;

import java.util.List;
import java.util.Random;

public class Enemy {
    private int x, y, velocity;
    private boolean alive = true;

    // Bounded area of the enemy
    private final int LOWER_XBOUND = 100, UPPER_XBOUND = 900;
    private final int LOWER_YBOUND = 100, UPPER_YBOUND = 500;

    // Squared distance is used because square root is costly.
    // Within 104 units from a note is considered a collusion.
    private final int SQUARED_COLLUSION_DISTANCE = 104 * 104;
    private final Image ENEMY_IMAGE = ImageCacher.getImage("res/enemy.png");

    public Enemy() {
        // Choosing a random location & direction.
        this.x = Utils.getRandomNumber(LOWER_XBOUND, UPPER_XBOUND);
        this.y = Utils.getRandomNumber(LOWER_YBOUND, UPPER_YBOUND);
        this.velocity = new Random().nextBoolean() ? 1 : -1;
    }

    public void update(List<AbstractNote> notes) {
        if (!alive) return;

        // Collusion handling for main.java.notes.
        notes.forEach(this::processNote);

        // Revert direction on reaching a boundary.
        if (x < LOWER_XBOUND) velocity = 1;
        if (x > UPPER_XBOUND) velocity = -1;

        x += velocity;

        ENEMY_IMAGE.draw(x, y);
    }

    public void processNote(AbstractNote note) {
        // Don't process destroyed main.java.notes, or special main.java.notes.
        if (note.isDestroyed() || note.getNoteType().isSpecial()) return;

        double squaredDistance = Utils.squaredDistance(
                note.getNoteXCoord(),
                note.getNoteYCoord(),
                x,
                y
        );

        if (squaredDistance < SQUARED_COLLUSION_DISTANCE) note.destroy();
    }

    public boolean isAlive() {
        return alive;
    }

    public void destroy() {
        this.alive = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
