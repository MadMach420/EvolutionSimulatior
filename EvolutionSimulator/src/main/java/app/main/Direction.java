package app.main;

public enum Direction {
    NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST;

    public Direction next() {
        return Direction.values()[(this.ordinal() + 1) % 8];
    }

    public Vector2D toVector() {
        return switch (this) {
            case NORTH -> new Vector2D(0, 1);
            case NORTHEAST -> new Vector2D(1, 1);
            case EAST -> new Vector2D(1, 0);
            case SOUTHEAST -> new Vector2D(1, -1);
            case SOUTH -> new Vector2D(0, -1);
            case SOUTHWEST -> new Vector2D(-1, -1);
            case WEST -> new Vector2D(-1, 0);
            case NORTHWEST -> new Vector2D(-1, 1);
        };
    }
}
