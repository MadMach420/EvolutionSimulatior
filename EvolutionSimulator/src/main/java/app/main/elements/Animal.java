package app.main.elements;

import app.main.Direction;
import app.main.Genome;
import app.main.Vector2D;
import app.main.maps.AbstractMap;
import app.main.observers.AnimalDeathObserver;
import app.main.observers.PositionChangeObserver;

import java.util.LinkedList;

public class Animal implements Comparable<Animal>{
    protected Vector2D position;
    AbstractMap map;
    public Direction direction = Direction.values()[(int) Math.floor(Math.random() * 8)];
    public int energy;
    public Genome genome;
    public int age = 0;
    public int children = 0;
    public int energyLoss;
    private final LinkedList<AnimalDeathObserver> deathObservers = new LinkedList<>();
    private final LinkedList<PositionChangeObserver> positionObservers = new LinkedList<>();

    public Animal(Vector2D position, AbstractMap map, int energy, int energyLoss) {
        this.position = position;
        this.map = map;
        this.energy = energy;
        this.energyLoss = energyLoss;
    }

    public Animal(Vector2D position, AbstractMap map, int energy, int energyLoss, Genome genome) {
        this(position, map, energy, energyLoss);
        this.genome = genome;
    }

    public void move() {
        energy--;
        age++;
        this.rotate(this.genome.getGene());
        Vector2D oldPosition = this.getPosition();
        Vector2D newPosition = this.position.add(this.direction.toVector());
        if (newPosition.y < 0 || map.height <= newPosition.y) {
            this.rotate(4);
        } else if (newPosition.x < 0 || map.width <= newPosition.x) {
            if (map.wrapEdges) {
                this.position = new Vector2D(newPosition.x % map.width, newPosition.y);
            } else {
                this.energy -= this.energyLoss;
                this.position = new Vector2D(map.width, map.height, true);
            }
        } else {
            this.position = newPosition;
        }
        positionChange(oldPosition, this.getPosition());
    }

    private void rotate(int n) {
        for (int i = 0; i < n; i++) {
            direction = direction.next();
        }
    }

    public Vector2D getPosition() {
        return position;
    }

    public boolean checkDeath() {
        return this.energy <= 0;
    }

    public Animal breed(Animal other) {
        this.energy -= energyLoss;
        other.energy -= energyLoss;
        this.children++;
        other.children++;
        boolean startFromLeft = Math.random() < 0.5;
        int[] newGenomeArray = new int[this.genome.getLength()];
        for (int i = 0; i < newGenomeArray.length; i++) {
            if (startFromLeft) {
                if (i < newGenomeArray.length * (this.energy / (this.energy + other.energy))) {
                    newGenomeArray[i] = this.genome.getGeneAtIndex(i);
                } else {
                    newGenomeArray[i] = other.genome.getGeneAtIndex(i);
                }
            } else {
                if (i < newGenomeArray.length * (other.energy / (this.energy + other.energy))) {
                    newGenomeArray[i] = other.genome.getGeneAtIndex(i);
                } else {
                    newGenomeArray[i] = this.genome.getGeneAtIndex(i);
                }
            }
        }
        Genome newGenome = new Genome(newGenomeArray, this.genome.randomMutation, this.genome.jumpToRandomGene, this.genome.minMutations, this.genome.maxMutations);

        return new Animal(this.getPosition(), this.map, 2 * energyLoss, energyLoss, newGenome);
    }

    public void eat(int energyGain) {
        this.energy += energyGain;
    }

    public void addObserver(Object observer) {
        if (observer instanceof AnimalDeathObserver) {
            deathObservers.add((AnimalDeathObserver) observer);
        }
        if (observer instanceof PositionChangeObserver) {
            positionObservers.add((PositionChangeObserver) observer);
        }
    }

    public void removeObserver(Object observer) {
        if (observer instanceof AnimalDeathObserver) {
            deathObservers.remove((AnimalDeathObserver) observer);
        }
        if (observer instanceof PositionChangeObserver) {
            positionObservers.remove((PositionChangeObserver) observer);
        }
    }

    private void positionChange(Vector2D oldPosition, Vector2D newPosition) {
        positionObservers.forEach(elem -> {
            elem.positionChange(this, oldPosition, newPosition);
        });
    }

    public void death() {
        deathObservers.forEach(elem -> {
            elem.deathOfAnimal(this);
        });
    }

    @Override
    public int compareTo(Animal o) {
        if (this.energy - o.energy != 0) {
            return this.energy - o.energy;
        } else if (this.age - o.age != 0) {
            return this.age - o.age;
        } else if (this.children - o.children != 0) {
            return this.children - o.children;
        } else return 0;
    }
}
