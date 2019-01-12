package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import sample.Calculations.MovementDataPackage;
import sample.Calculations.SpaceShip;
import sample.simulation.SpaceShipUpdateService;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private boolean increaseSpeed;
    private boolean decreaseSpeed;
    private SpaceShip spaceShip;
    // Usage of fuel per one second. Fuel usage can't be positive number.
    private static double fuelUsage;

    @FXML
    private Pane game_pane;

    @FXML
    private Label dataLabel;
    @FXML
    private Label fuelUsageLabel;

    @FXML
    private Label velocityLabel;

    @FXML
    private Label massLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spaceShip = new SpaceShip();
        changeSpeedInit();
        graphicsInit();
        SpaceShipUpdateService spaceShipUpdateService = new SpaceShipUpdateService(spaceShip);
        spaceShipUpdateService.setPeriod(Duration.seconds(0.1));
        spaceShipUpdateService.setOnSucceeded(event -> System.out.println("Updated"));
        spaceShipUpdateService.setOnFailed(event -> System.out.println("Failed"));
        spaceShipUpdateService.start();
        MovementDataPackage movementDataPackage = new MovementDataPackage(dataLabel,velocityLabel,massLabel,fuelUsageLabel);
        spaceShip.addObserver(movementDataPackage);
    }


    /**
     * @return fuelUsage in kilograms per second
     */
    public static double getFuelUsage() {
        return fuelUsage;
    }

    /**
     * Initialization of listeners on main game pane
     */
    private void changeSpeedInit() {
        game_pane.sceneProperty().addListener((observableValue, scene, t1) -> {
            game_pane.getScene().setOnKeyPressed(keyEvent -> {
                switch (keyEvent.getCode()) {
                    case UP: {
                        increaseSpeed = true;
                        if (fuelUsage>SpaceShip.MAXIMUM_FUEL_USAGE) //maximum burnout rate
                        fuelUsage-=SpaceShip.CHANGE_OF_FUEL_USAGE; //grams
                            System.out.println(fuelUsage);
                        break;
                    }
                    case DOWN:
                        decreaseSpeed = true;
                        if (fuelUsage!=0) //fuel usage can't be positive
                        fuelUsage+= SpaceShip.CHANGE_OF_FUEL_USAGE; //grams
                            System.out.println(fuelUsage);
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


    private void graphicsInit(){

        Canvas canvas = new Canvas( 1000, 500);
        game_pane.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Sprite background = new Sprite(0,0,0,0);
        Image image = new Image("sample/images/background.png");
        background.setImage(image);
        Sprite followingBackground = new Sprite(0,image.getHeight(),0,0);
        followingBackground.setImage(image);
        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                if (spaceShip.isHasLanded()) this.stop();
                System.out.println(background.getPositionY());
                if (background.getPositionY()<-image.getHeight()){
                    background.setPositionY(image.getHeight());
                }
                else if (background.getPositionY()>image.getHeight())
                    background.setPositionY(-image.getHeight());
                else if (followingBackground.getPositionY()<-image.getHeight()){
                    followingBackground.setPositionY(image.getHeight());
                }
                else if (followingBackground.getPositionY()>image.getHeight())
                    followingBackground.setPositionY(-image.getHeight());
                // background image clears canvas
                background.setVelocity(0,spaceShip.getVelocityStart()/150);
                background.update(t);
                background.render(gc);
                followingBackground.setVelocity(0,spaceShip.getVelocityStart()/150);
                followingBackground.update(t);
                followingBackground.render(gc);
            }
        }.start();

    }

}