package app.main.elements;

import app.main.Vector2D;
import app.main.observers.AnimalDeathObserver;

public class DeathSpot implements Comparable<DeathSpot> {
    public int deathCount = 0;
    public Vector2D spot;

    public DeathSpot(Vector2D spot) {
        this.spot = spot;
    }

    @Override
    public int compareTo(DeathSpot o) {
        if (this.deathCount - o.deathCount != 0) {
            return this.deathCount - o.deathCount;
        } else if (this.spot.x - o.spot.x != 0) {
            return this.spot.x - o.spot.x;
        } else if (this.spot.y - o.spot.y != 0) {
            return this.spot.y - o.spot.y;
        } else return 0;
    }
}
