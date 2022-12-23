package app.main.elements;

import app.main.Vector2D;
import app.main.observers.AnimalDeathObserver;
import app.main.observers.PositionChangeObserver;

import java.util.HashMap;
import java.util.TreeSet;

public class AnimalsByPosition implements PositionChangeObserver {
    public int animalCount = 0;
    public HashMap<Vector2D, TreeSet<Animal>> animalsByPosition;

    public void addAnimal(Animal animal) {
        animal.addObserver(this);
        animalsByPosition.get(animal.getPosition()).add(animal);
        animalCount++;
    }

    public void removeAnimal(Animal animal) {
        animal.removeObserver(this);
        animalsByPosition.get(animal.getPosition()).remove(animal);
        animalCount--;
    }

    public TreeSet<Animal> getAnimalsAtPosition(Vector2D position) {
        return animalsByPosition.get(position);
    }

    @Override
    public void positionChange(Animal animal, Vector2D oldPosition, Vector2D newPosition) {
        animalsByPosition.get(oldPosition).remove(animal);
        animalsByPosition.get(newPosition).add(animal);
    }
}
