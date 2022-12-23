package app.main.maps;

import app.main.Vector2D;

import java.util.Collections;
import java.util.LinkedList;

public class EquatorMap extends AbstractMap{
    private final int equatorYMin;
    private final int equatorYMax;

    public EquatorMap(int width, int height) {
        this.width = width;
        this.height = height;
        int halfEquatorHeight = (int) Math.floor(height / 10.0);
        this.equatorYMin = (int) Math.floor(height /2.0) - halfEquatorHeight;
        this.equatorYMax = (int) Math.floor(height /2.0) + halfEquatorHeight;
    }

    public EquatorMap(int width, int height, boolean wrapEdges) {
        this(width, height);
        this.wrapEdges = wrapEdges;
    }

    protected boolean spawnGrassPreferred() {
        LinkedList<Vector2D> spots = new LinkedList<>();
        for (int i = 0; i < width; i++) {
            for (int j = equatorYMin; j <= equatorYMax; j++) {
                Vector2D newSpot = new Vector2D(i, j);
                if (!grassSet.contains(newSpot)){
                    spots.add(new Vector2D(i, j));
                }
            }
        }
        if (spots.size() > 0) {
            Collections.shuffle(spots);
            grassSet.add(spots.get(0));
            return true;
        } else {
            return false;
        }
    }

    protected boolean spawnGrassNonPreferred() {
        LinkedList<Vector2D> spots = new LinkedList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < equatorYMin; j++) {
                Vector2D newSpot = new Vector2D(i, j);
                if (!grassSet.contains(newSpot)){
                    spots.add(new Vector2D(i, j));
                }
            }
            for (int j = equatorYMax + 1; j < height; j++) {
                Vector2D newSpot = new Vector2D(i, j);
                if (!grassSet.contains(newSpot)){
                    spots.add(new Vector2D(i, j));
                }
            }
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
