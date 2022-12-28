package app.main.maps;

import app.main.Vector2D;
import app.main.elements.Animal;
import app.main.elements.DeathSpot;
import app.main.observers.AnimalDeathObserver;

import java.util.*;

public class ToxicMap extends AbstractMap {
    private final TreeSet<DeathSpot> deathSpots = new TreeSet<>();
    private final HashMap<Vector2D, DeathSpot> deathSpotsMap = new HashMap<>();

    public ToxicMap(int width, int height) {
        this.width = width;
        this.height = height;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                DeathSpot newDeathSpot = new DeathSpot(new Vector2D(i, j));
                deathSpots.add(newDeathSpot);
                deathSpotsMap.put(newDeathSpot.spot, newDeathSpot);
            }
        }
    }

    public ToxicMap(int width, int height, boolean wrapEdges) {
        this(width, height);
        this.wrapEdges = wrapEdges;
    }

    public void removeAnimal(Animal animal) {
        super.removeAnimal(animal);
        deathSpotsMap.get(animal.getPosition()).deathCount++;
    }

    @Override
    protected boolean spawnGrassPreferred() {
        LinkedList<Vector2D> spots = new LinkedList<>();
        int cutoff = (int) Math.floor(0.2 * deathSpots.size());
        int i = 0;
        Iterator<DeathSpot> iterator = deathSpots.iterator();
        while (i <= cutoff) {
            DeathSpot currDeathSpot = iterator.next();
            if (!grassSet.contains(currDeathSpot.spot)) {
                spots.add(currDeathSpot.spot);
            }
            i++;
        }
        if (spots.size() > 0) {
            Collections.shuffle(spots);
            grassSet.add(spots.get(0));
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean spawnGrassNonPreferred() {
        LinkedList<Vector2D> spots = new LinkedList<>();
        int cutoff = (int) Math.floor(0.2 * deathSpots.size());
        int i = 0;
        for (DeathSpot deathSpot : deathSpots) {
            if (i > cutoff) {
                if (!grassSet.contains(deathSpot.spot)) {
                    spots.add(deathSpot.spot);
                }
            }
            i++;
        }
        if (spots.size() > 0) {
            Collections.shuffle(spots);
            grassSet.add(spots.get(0));
            return true;
        } else {
            return false;
        }
    }
}
