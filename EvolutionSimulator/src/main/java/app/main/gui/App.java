package app.main.gui;

import app.main.Engine;
import app.main.RunConfigs;
import app.main.observers.EngineObserver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class App extends Application implements EngineObserver {
    Engine engine;
    DrawElements draw;
    int delay = 500;
    boolean running = false;
    HBox hBox;

    @Override
    public void start(Stage primaryStage) throws Exception {
        hBox = new HBox();
        Scene scene = new Scene(hBox);
        primaryStage.setScene(scene);
        primaryStage.show();
        drawInitialControls();
    }

    public void drawInitialControls() {
        VBox vBox = new VBox();
        Label label = new Label("Select simulation");
        Button button1 = new Button("Default 1");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                initializeEngineFromFile("./src/main/resources/configs/default1.csv");
            }
        });
        Button button2 = new Button("Default 2");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                initializeEngineFromFile("./src/main/resources/configs/default2.csv");
            }
        });
        Button button3 = new Button("Custom simulation");
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                drawForm();
            }
        });
        vBox.getChildren().addAll(label, button1, button2, button3);
        hBox.getChildren().add(0, vBox);
    }

    public void drawForm() {
        hBox.getChildren().remove(0);
        String[] labelTexts = new String[]{
                "Map width", "Map height", "Starting grass", "Starting animals",
                "Map variant", "Grass growth variant", "Grass growth per day", "Energy from grass",
                "Energy required to breed", "Starting energy", "Energy lost by breeding", "Genome length",
                "Min mutations", "Max mutations", "Fully randomise mutation", "Randomise gene order"
        };
        VBox vBox = new VBox();
        for (String text : labelTexts) {
            HBox controlBox = new HBox();
            Label label = new Label(text);
            controlBox.getChildren().add(label);
            switch (text) {
                case "Map variant" -> {
                    ChoiceBox<String> box = new ChoiceBox<>(FXCollections.observableArrayList(
                            "earth", "inferno"
                    ));
                    controlBox.getChildren().add(box);
                    break;
                }
                case "Grass growth variant" -> {
                    ChoiceBox<String> box = new ChoiceBox<>(FXCollections.observableArrayList(
                            "equator", "toxic"
                    ));
                    controlBox.getChildren().add(box);
                    break;
                }
                case "Fully randomise mutation", "Randomise gene order" -> {
                    ChoiceBox<String> box = new ChoiceBox<>(FXCollections.observableArrayList(
                            "true", "false"
                    ));
                    controlBox.getChildren().add(box);
                    break;
                }
                default -> {
                    TextField field = new TextField();
                    controlBox.getChildren().add(field);
                }
            }
            vBox.getChildren().add(controlBox);
        }
        Button button = new Button("Run simulation");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<String> parametersList = new ArrayList<>();
                vBox.getChildren().forEach(controlBox -> {
                    if (controlBox instanceof HBox) {
                        Object control = ((HBox) controlBox).getChildren().get(1);
                        if (control instanceof TextField) {
                            parametersList.add(((TextField) control).getText());
                        } else if (control instanceof ChoiceBox) {
                            parametersList.add((String) ((ChoiceBox<?>) control).getValue());
                        }
                    }
                });
                String[] parameters = parametersList.toArray(new String[0]);
                initializeCustomEngine(parameters);
            }
        });
        vBox.getChildren().add(button);
        hBox.getChildren().add(0, vBox);
    }

    public void initializeEngineFromFile(String path) {
        try {
            engine = RunConfigs.fromFile(path);
            engine.addObserver(this);
            draw = new DrawElements(engine);
            Thread engineThread = new Thread(engine);
            engineThread.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void initializeCustomEngine(String[] parameters) {
        engine = RunConfigs.fromParameters(parameters);
        engine.addObserver(this);
        draw = new DrawElements(engine);
        Thread engineThread = new Thread(engine);
        engineThread.start();
    }

    public void drawStage() {
        hBox.getChildren().clear();
        hBox.getChildren().add(draw.drawMap());
        hBox.getChildren().add(draw.drawStatistics());
    }

    @Override
    public void engineStep() {
        Platform.runLater(this::drawStage);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            System.out.println("Simulation interrupted");
        }
    }
}
