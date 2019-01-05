package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import sample.Calculations.SpaceShip;


import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class Controller implements Initializable, Observer {
    private boolean increaseSpeed, decreaseSpeed;
    private SpaceShip spaceShip;
    private double u;
    @FXML
    private Pane game_pane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spaceShip = new SpaceShip();
        changeSpeedInit();
        graphicsInit();
    }

    @Override
    public void update(Observable observable, Object o) {
        spaceShip = (SpaceShip) observable;


    }

    public void changeSpeedInit() {
        game_pane.sceneProperty().addListener((observableValue, scene, t1) -> {
            game_pane.getScene().setOnKeyPressed(keyEvent -> {
                switch (keyEvent.getCode()) {
                    case UP: {
                        increaseSpeed = true;
                        u++;
                        spaceShip.setCurrentU(u);
                        break;
                    }
                    case DOWN:
                        decreaseSpeed = true;
                        break;
                }

            });

            game_pane.getScene().setOnKeyReleased(keyEvent -> {
                switch (keyEvent.getCode()) {
                    case UP:
                        increaseSpeed = false;
                        break;
                    case DOWN:
                        decreaseSpeed = false;
                        break;
                }

            });

        });

    }
    public void graphicsInit(){

        Canvas canvas = new Canvas( 1000, 1000);
        game_pane.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(new Image("sample/images/background.jpg"),0,0);
        gc.stroke();

    }

}