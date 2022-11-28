package app.main;

public enum Direction {
    FORWARD, FORWARDRIGHT, RIGHT, BACKRIGHT, BACK, BACKLEFT, LEFT, FORWARDLEFT;

    public Direction rotate() {
        return Direction.values()[(this.ordinal() + 1) % 8];
    }

    public Vector2D toVector() {}
}
