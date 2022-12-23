package app.main.observers;

import app.main.Vector2D;
import app.main.elements.Animal;

public interface PositionChangeObserver {
    public void positionChange(Animal animal, Vector2D oldPosition, Vector2D newPosition);
}
