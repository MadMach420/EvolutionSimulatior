package app.main.elements;

import app.main.Vector2D;

import app.main.maps.AbstractMap;
import app.main.observers.PositionChangeObserver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

public class AnimalsByPosition implements PositionChangeObserver {
    public int animalCount = 0;
    public HashMap<Vector2D, TreeSet<Animal>> animalsByPosition = new HashMap<>();
    public LinkedList<Animal> allAnimals = new LinkedList<>();
    public float averageTimeOfDeath = 0;
    public int deadAnimals = 0;

    public void addAnimal(Animal animal) {
        animal.addObserver(this);
        animalsByPosition.computeIfAbsent(animal.getPosition(), k -> new TreeSet<>());
        animalsByPosition.get(animal.getPosition()).add(animal);
        allAnimals.add(animal);
        animalCount++;
    }

    public void removeAnimal(Animal animal) {
        animal.removeObserver(this);
        animalsByPosition.get(animal.getPosition()).remove(animal);
        allAnimals.remove(animal);
        animalCount--;
    }

    public Animal getBestAnimalAtPosition(Vector2D position) {
        if (animalsByPosition.get(position) != null && animalsByPosition.get(position).size() > 0) {
            return animalsByPosition.get(position).last();
        } else return null;
    }

    public void checkAnimalDeath() {
//        allAnimals.forEach(Animal::checkDeath);
        Iterator<Animal> i = allAnimals.iterator();
        while (i.hasNext()) {
            Animal animal = i.next();
            if (animal.checkDeath()) {
                i.remove();
                animal.death();
                averageTimeOfDeath = (averageTimeOfDeath * deadAnimals + animal.age) / (deadAnimals + 1);
                deadAnimals++;
            }
        }
    }

    public void moveAnimals() {
        allAnimals.forEach(Animal::move);
    }

    public void breedAnimals(int energyToBreed, AbstractMap map) {
        animalsByPosition.forEach((position, set) -> {
            if (set.size() >= 2) {
                Animal first = set.last();
                Animal second = set.lower(first);
                if (second != null && second.energy >= energyToBreed) {
                    map.addAnimal(first.breed(second));
                }
            }
        });
    }

    public boolean eatGrassAtPosition(Vector2D position, int energyGain) {
        TreeSet<Animal> animals = animalsByPosition.get(position);
        if (animals != null && animals.size() > 0) {
            animals.last().eat(energyGain);
            return true;
        } else {
            return false;
        }
    }

    public float getAverageEnergy() {
        int energy = 0;
        for (Animal animal : allAnimals) {
            energy += animal.energy;
        }
        return (float) energy / allAnimals.size();
    }

    @Override
    public void positionChange(Animal animal, Vector2D oldPosition, Vector2D newPosition) {
        animalsByPosition.get(oldPosition).remove(animal);
        animalsByPosition.computeIfAbsent(newPosition, k -> new TreeSet<>());
        animalsByPosition.get(newPosition).add(animal);
    }
}
