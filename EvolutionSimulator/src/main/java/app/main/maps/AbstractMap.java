package app.main.maps;

import app.main.Vector2D;
import app.main.elements.Animal;
import app.main.elements.AnimalsByPosition;
import app.main.observers.AnimalDeathObserver;

import java.util.HashSet;


public abstract class AbstractMap implements AnimalDeathObserver {
    public int width;
    public int height;
    public boolean wrapEdges = true;
    public HashSet<Vector2D> grassSet = new HashSet<>();
    public AnimalsByPosition animals = new AnimalsByPosition();

    private void spawnGrass() {
        boolean grassSpawned;
        if (Math.random() < 0.8) {
            grassSpawned = spawnGrassPreferred();
            if (!grassSpawned) spawnGrassNonPreferred();
        } else {
            grassSpawned = spawnGrassNonPreferred();
            if (!grassSpawned) spawnGrassPreferred();
        }
    }

    public void spawnMultipleGrass(int n) {
        for (int i = 0; i < n; i++) {
            this.spawnGrass();
        }
    }

    public void addAnimal(Animal animal) {
        animal.addObserver(this);
        animals.addAnimal(animal);
    }

    public void removeAnimal(Animal animal) {
        animal.removeObserver(this);
        animals.removeAnimal(animal);
    }

    public void checkAnimalDeath() {
        animals.checkAnimalDeath();
    }

    public void moveAnimals() {
        animals.moveAnimals();
    }

    public void eatGrass(int energyGain) {
        grassSet.removeIf(position -> animals.eatGrassAtPosition(position, energyGain));
    }

    public void breedAnimals(int energyToBreed) {
        animals.breedAnimals(energyToBreed, this);
    }

    public Animal animalAt(Vector2D position) {
        return animals.getBestAnimalAtPosition(position);
    }

    public boolean grassAt(Vector2D position) {
        return grassSet.contains(position);
    }

    protected abstract boolean spawnGrassPreferred();

    protected abstract boolean spawnGrassNonPreferred();

    @Override
    public void deathOfAnimal(Animal animal) {
        this.removeAnimal(animal);
    }
}
