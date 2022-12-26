package app.main.elements;

import app.main.Vector2D;

import app.main.maps.AbstractMap;
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

    public Animal getBestAnimalAtPosition(Vector2D position) {
        if (animalsByPosition.get(position) != null) {
            return animalsByPosition.get(position).last();
        } else return null;
    }

    public void checkAnimalDeath() {
        animalsByPosition.forEach((position, set) -> set.forEach(Animal::checkDeath));
    }

    public void moveAnimals() {
        animalsByPosition.forEach((position, set) -> set.forEach(Animal::move));
    }

    public void breedAnimals(int energyToBreed, AbstractMap map) {
        animalsByPosition.forEach((position, set) -> {
            if (set.size() >= 2) {
                Animal first = set.last();
                Animal second = set.lower(first);
                if (second.energy >= energyToBreed) {
                    map.addAnimal(first.breed(second));
                }
            }
        });
    }

    public boolean eatGrassAtPosition(Vector2D position, int energyGain) {
        TreeSet<Animal> animals = animalsByPosition.get(position);
        if (animals != null) {
            animals.last().eat(energyGain);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void positionChange(Animal animal, Vector2D oldPosition, Vector2D newPosition) {
        animalsByPosition.get(oldPosition).remove(animal);
        animalsByPosition.get(newPosition).add(animal);
    }
}
