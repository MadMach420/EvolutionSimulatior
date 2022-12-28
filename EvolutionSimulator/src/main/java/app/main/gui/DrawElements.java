package app.main.gui;

import app.main.Engine;
import app.main.Vector2D;
import app.main.elements.Animal;
import app.main.observers.EngineObserver;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class DrawElements {
    Engine engine;

    public DrawElements(Engine engine) {
        this.engine = engine;
    }

    public GridPane drawMap() {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < engine.map.width; i++) {
            for (int j = 0; j < engine.map.height; j++) {
                Rectangle rectangle = new Rectangle(0, 0, 20, 20);

                if (engine.map.grassAt(new Vector2D(i, j))) {
                    rectangle.setFill(Paint.valueOf("green"));
                } else {
                    rectangle.setFill(Paint.valueOf("brown"));
                }
                gridPane.add(rectangle, i, j);

                Animal animal = engine.map.animalAt(new Vector2D(i, j));
                if (animal != null) {
                    Circle circle = new Circle(10, 10, 10);
                    if (animal.energy > engine.minEnergyToBreed) {
                        circle.setFill(Paint.valueOf("blue"));
                    } else {
                        circle.setFill(Paint.valueOf("red"));
                    }
                    gridPane.add(circle, i, j);
                }
            }
        }
        return gridPane;
    }

    public VBox drawStatistics() {
        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                new Label("Animals: " + engine.map.animals.animalCount),
                new Label("Grass: " + engine.map.grassSet.size()),
                new Label("Free spaces: " + engine.map.getNumberOfFreeSpaces()),
                new Label("Average energy: " + engine.map.animals.getAverageEnergy()),
                new Label("Average age of death: " + engine.map.animals.averageTimeOfDeath)
        );
        return vBox;
    }
}
