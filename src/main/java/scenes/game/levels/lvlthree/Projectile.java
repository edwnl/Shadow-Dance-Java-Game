package main.java.scenes.game.levels.lvlthree;

import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import main.java.utils.ImageCacher;
import main.java.utils.Utils;

import java.util.List;

public class Projectile {
    // DrawOptions allows Bagel to rotate an image.
    private final DrawOptions DRAW_OPTIONS;
    protected final Image PROJECTILE_IMAGE = ImageCacher.getImage("res/arrow.png");
    private static final int ARROW_SPEED = 6;

    // Start the arrow at the Guardian's location.
    private int x = Guardian.GUARDIAN_X, y = Guardian.GUARDIAN_Y;

    // Rotation in radians of the arrow.
    private final double ROTATION;
    private boolean isDestroyed;

    // Squared distance is used because square root is costly.
    // Within 62 units from an enemy is considered a collusion.
    private final int SQUARED_COLLUSION_DISTANCE = 62 * 62;

    Projectile(Enemy enemy) {
        this.ROTATION = calculateAngle(enemy);
        this.DRAW_OPTIONS = new DrawOptions().setRotation(ROTATION);
    }

    /**
     * Mathematically calculate the angle between an enemy and the guardian.
     * Arctangent is used.
     * @return angle in radians.
     */
    public static double calculateAngle(Enemy enemy) {
        // Calculate the difference in x and y coordinates
        double deltaX = enemy.getX() - Guardian.GUARDIAN_X;
        double deltaY = enemy.getY() - Guardian.GUARDIAN_Y;

        // Calculate the angle using arctangent (atan2)
        return Math.atan2(deltaY, deltaX);
    }

    public void update(List<Enemy> enemies) {
        if (isDestroyed) return;

        // Calculate the next x & y coordinate based on the angle.
        x += ARROW_SPEED * Math.cos(ROTATION);
        y += ARROW_SPEED * Math.sin(ROTATION);

        // Destroy out of bounds arrows
        if (x < 0 || x > Window.getWidth() || y < 0 || y > Window.getHeight()) {
            destroy();
        }

        PROJECTILE_IMAGE.draw(x, y, DRAW_OPTIONS);

        // Handle collusion for the enemies.
        enemies.forEach(this::processEnemy);
    }

    /**
     * Remove enemies if they are hit by the arrow.
     */
    public void processEnemy(Enemy enemy) {
        double squaredDistance = Utils.squaredDistance(
                enemy.getX(),
                enemy.getY(),
                x,
                y
        );

        if (squaredDistance < SQUARED_COLLUSION_DISTANCE) {
            enemy.destroy();
            destroy();
        }
    }

    public void destroy() {
        this.isDestroyed = false;
    }
}
