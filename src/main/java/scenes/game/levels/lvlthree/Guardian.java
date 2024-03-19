package main.java.scenes.game.levels.lvlthree;

import bagel.Image;
import bagel.Input;
import bagel.Keys;
import main.java.notes.AbstractNote;
import main.java.utils.ImageCacher;
import main.java.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Guardian {
    private final List<Projectile> PROJECTILES = new ArrayList<>();
    private final List<Enemy> ENEMIES = new ArrayList<>();
    private final int SPAWN_INTERVAL = 600;
    private final Image GUARDIAN_IMAGE = ImageCacher.getImage("res/guardian.png");
    // Guardian Location
    public static final int GUARDIAN_X = 800, GUARDIAN_Y = 600;

    public void reset() {
        for (Projectile proj : PROJECTILES) {
            proj.destroy();
        }

        for (Enemy enemy : ENEMIES) {
            enemy.destroy();
        }
    }

    public void update(Input input, int frameNum, List<AbstractNote> allNotes) {
        GUARDIAN_IMAGE.draw(GUARDIAN_X, GUARDIAN_Y);

        // Spawn an enemy every x frames
        if (frameNum % SPAWN_INTERVAL == 0) {
            ENEMIES.add(new Enemy());
        }

        // Handling the movement of enemies, and the destruction of main.java.notes.
        ENEMIES.forEach(enemy -> enemy.update(allNotes));

        if (input.wasPressed(Keys.LEFT_SHIFT)) fireArrow(ENEMIES);

        // Handling the movement of arrows, and the destruction of enemies.
        PROJECTILES.forEach(projectile -> projectile.update(ENEMIES));
    }

    public void fireArrow(List<Enemy> ENEMIES) {
        Enemy closestEnemy = null;
        double closestDistance = Double.MAX_VALUE;

        // Finding the closest enemy
        for (Enemy enemy : ENEMIES) {
            if (!enemy.isAlive()) continue;
            double distanceFromGuardian = Utils.squaredDistance(
                    GUARDIAN_X,
                    GUARDIAN_Y,
                    enemy.getX(),
                    enemy.getY()
            );

            if (distanceFromGuardian < closestDistance) {
                closestEnemy = enemy;
                closestDistance = distanceFromGuardian;
            }
        }

        if (closestEnemy == null) return;

        // Fire and track the projectile if the closest enemy is found.
        PROJECTILES.add(new Projectile(closestEnemy));
    }
}
