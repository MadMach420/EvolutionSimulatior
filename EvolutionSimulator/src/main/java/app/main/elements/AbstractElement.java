package app.main.elements;

import app.main.Vector2D;
import app.main.maps.AbstractMap;

public class AbstractElement {
    protected Vector2D position;
    private AbstractMap map;

    public Vector2D getPosition() {
        return position;
    }
}
