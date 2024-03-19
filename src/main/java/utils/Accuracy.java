package main.java.utils;

/**
 * Enum representing different accuracy texts,
 * and the score they represent.
 */
public enum Accuracy {
    PERFECT(10),
    GOOD(5),
    BAD(-1),
    MISS(-5);

    private int scoreValue;

    Accuracy(int score) {
        this.scoreValue = score;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    /**
     * Returns the accuracy given a distance from the target.
     * @param dist Distance from target stationary note.
     */
    public static Accuracy getAccuracyFromDistance(int dist) {
        if (dist < 0) return Accuracy.MISS;
        else if (dist <= 15) return Accuracy.PERFECT;
        else if (dist <= 50) return Accuracy.GOOD;
        else if (dist <= 100) return Accuracy.BAD;
        else return Accuracy.MISS;
    }
}
