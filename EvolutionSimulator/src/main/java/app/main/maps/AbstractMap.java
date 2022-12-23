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

    public void spawnGrass() {
        boolean grassSpawned = false;
        if (Math.random() < 0.8) {
            grassSpawned = spawnGrassPreferred();
            if (!grassSpawned) spawnGrassNonPreferred();
        } else {
            grassSpawned = spawnGrassNonPreferred();
            if (!grassSpawned) spawnGrassPreferred();
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

    protected abstract boolean spawnGrassPreferred();

    protected abstract boolean spawnGrassNonPreferred();

    @Override
    public void deathOfAnimal(Animal animal) {
        this.removeAnimal(animal);
    }
}
