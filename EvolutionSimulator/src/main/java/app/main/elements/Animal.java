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

    public Animal(Vector2D position, AbstractMap map, int energy, int energyLoss, int genomeLength) {
        this(position, map, energy, energyLoss);
        this.genome = new Genome(genomeLength);
    }

    public Animal(Vector2D position, AbstractMap map, int energy, int energyLoss, Genome genome) {
        this(position, map, energy, energyLoss);
        this.genome = genome;
    }

    public void move() {
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

    public void checkDeath() {
        if (this.energy <= 0) {
            // TODO
            //  ŚMIERĆ
        }
    }

    public Animal breed(Animal other) {
        // TODO
        //  Genom: podział na 2, stworzenie;
        //  odjęcie energii rodzicom, skopiowanie parametrów dla dziecka;
        //  return new Animal(genom i parametry);
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

    private void death() {
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
