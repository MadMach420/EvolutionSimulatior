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
        } else return Math.random() < 0.5 ? -1 : 1; // losowa kolejność w przypadku braku trupów
    }
}
