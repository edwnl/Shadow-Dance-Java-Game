package main.java.lane;

import bagel.Image;
import bagel.Keys;
import main.java.utils.ImageCacher;

/**
 * Enum representing all the types of a lane.
 */
public enum LaneType {
    Up(Keys.UP),
    Down(Keys.DOWN),
    Right(Keys.RIGHT),
    Left(Keys.LEFT),
    Special(Keys.SPACE);

    // Key required for this direction.
    final Keys KEY;
    // Coordinate of the main.java.notes and lanes.
    int laneXCoordinate;

    LaneType(Keys key) {
        this.KEY = key;
    }

    public Keys getKEY() {
        return KEY;
    }

    public void setXCoord(int x_coord) {
        this.laneXCoordinate = x_coord;
    }

    public int getXCoord() {
        return laneXCoordinate;
    }

    public Image getLaneImage() {
        return ImageCacher.getImage("res/lane" + this + ".png");
    }
}
