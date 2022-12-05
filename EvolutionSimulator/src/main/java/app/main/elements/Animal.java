package app.main.elements;

import app.main.Direction;
import app.main.Genome;
import app.main.Vector2D;

public class Animal extends AbstractElement {
    public Direction direction = Direction.values()[(int) Math.floor(Math.random() * 8)];
    public int energy;
    public Genome genome;
    public int age = 0;
    public int children = 0;

    public Animal(Vector2D position, int energy, int genomeLength) {
        this.position = position;
        this.energy = energy;
        this.genome = new Genome(genomeLength);
    }

    public void move() {
        // Jak odróżnić mapy? (można przypisać mapę do zwierzęcia)
        this.rotate(this.genome.getGene());
        this.position = this.position.add(this.direction.toVector());
        // TODO
        //  Jak odróżnić mapy? (można przypisać mapę do zwierzęcia)
        //  Po wykminieniu zmienić aktualizacje pozycji w zależności od mapy
    }

    private void rotate(int n) {
        for (int i = 0; i < n; i++) {
            direction = direction.next();
        }
    }
}
