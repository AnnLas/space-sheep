package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import sample.Calculations.MovementDataPackage;
import sample.Calculations.SpaceShip;
import sample.simulation.SpaceShipUpdateService;


import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class Controller implements Initializable, Observer {
    private SpaceShip spaceShip;
    // Usage of fuel per one second. Fuel usage can't be positive number.
    private static double fuelUsage;
    //scaler for animations
    private static final double VELOCITY_SCALER = 1500;
    //data series describing spaceship movement (v,h) which are added to the chart
    private XYChart.Series<Double, Double> movementSeries;


    @FXML
    private ScatterChart<Double, Double> movement_chart;
    @FXML
    private Pane game_pane;
    @FXML
    private Pane side_view_pane;

    @FXML
    private Label heightLabel;
    @FXML
    private Label fuelUsageLabel;

    @FXML
    private Label velocityLabel;

    @FXML
    private Label massLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spaceShip = new SpaceShip();
        spaceShip.addObserver(this);
        changeSpeedInit();
        graphicsInit();
        chartInit();
        SpaceShipUpdateService spaceShipUpdateService = new SpaceShipUpdateService(spaceShip);
        spaceShipUpdateService.setPeriod(Duration.seconds(0.1));
        spaceShipUpdateService.setOnSucceeded(event -> System.out.println("Updated"));
        spaceShipUpdateService.setOnFailed(event -> System.out.println("Failed"));
        spaceShipUpdateService.start();
        MovementDataPackage movementDataPackage = new MovementDataPackage(heightLabel, velocityLabel, massLabel, fuelUsageLabel);
        spaceShip.addObserver(movementDataPackage);
    }

    /**
     * adding series of data to the chart
     */
    private void chartInit() {
        movementSeries = new XYChart.Series<>();
        movement_chart.getData().add(movementSeries);
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
                        if (fuelUsage > SpaceShip.MAXIMUM_FUEL_USAGE) //maximum burnout rate
                            fuelUsage -= SpaceShip.CHANGE_OF_FUEL_USAGE; //grams
                        System.out.println(fuelUsage);
                        break;
                    }
                    case DOWN:
                        if (fuelUsage != 0) //fuel usage can't be positive
                            fuelUsage += SpaceShip.CHANGE_OF_FUEL_USAGE; //grams
                        System.out.println(fuelUsage);
                        break;
                }

            });


        });

    }


    private void graphicsInit() {

        final int height = 468;
        final int width = 615;


        Canvas canvas = new Canvas(width, height);
        Canvas sideViewCanva = new Canvas(width, 333);
        game_pane.getChildren().add(canvas);
        side_view_pane.getChildren().add(sideViewCanva);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext sideViewGC = sideViewCanva.getGraphicsContext2D();

        Sprite background = new BackgroundSprite(0, 0, 0, 0);


        Sprite ship = new Sprite(width / 4, 4, 0, 0);
        Image rocket = new Image("sample/images/small_rocket.png");

        Image image = new Image("sample/images/background.png");
        background.setImage(image);


        ship.setImage(new Image("sample/images/rocket0.png"));

        Sprite followingBackground = new BackgroundSprite(0, image.getHeight(), 0, 0);


        followingBackground.setImage(image);
        final long startNanoTime = System.nanoTime();

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                if (spaceShip.isHasLanded()) this.stop();
                System.out.println(background.getPositionY());
                // background image clears canvas
                if (spaceShip.getCurrentMass() > 1000500 && spaceShip.getCurrentHeight() > 0.5) {

                    if (0 == spaceShip.getCurrentFuelUsage()) {
                        ship.setImage(new Image("sample/images/rocket0.png"));
                    } else if (-32 * SpaceShip.CHANGE_OF_FUEL_USAGE >= spaceShip.getCurrentFuelUsage()) {
                        ship.setImage(new Image("sample/images/rocket1000.png"));
                    } else if (-28 * SpaceShip.CHANGE_OF_FUEL_USAGE >= spaceShip.getCurrentFuelUsage()) {
                        ship.setImage(new Image("sample/images/rocket875.png"));
                    } else if (-24 * SpaceShip.CHANGE_OF_FUEL_USAGE >= spaceShip.getCurrentFuelUsage()) {
                        ship.setImage(new Image("sample/images/rocket750.png"));
                    } else if (-20 * SpaceShip.CHANGE_OF_FUEL_USAGE >= spaceShip.getCurrentFuelUsage()) {
                        ship.setImage(new Image("sample/images/rocket625.png"));
                    } else if (-16 * SpaceShip.CHANGE_OF_FUEL_USAGE >= spaceShip.getCurrentFuelUsage()) {
                        ship.setImage(new Image("sample/images/rocket500.png"));
                    } else if (-12 * SpaceShip.CHANGE_OF_FUEL_USAGE >= spaceShip.getCurrentFuelUsage()) {
                        ship.setImage(new Image("sample/images/rocket375.png"));
                    } else if (-8 * SpaceShip.CHANGE_OF_FUEL_USAGE >= spaceShip.getCurrentFuelUsage()) {
                        ship.setImage(new Image("sample/images/rocket250.png"));
                    } else if (-4 * SpaceShip.CHANGE_OF_FUEL_USAGE >= spaceShip.getCurrentFuelUsage()) {
                        ship.setImage(new Image("sample/images/rocket125.png"));
                    }


                } else {
                    ship.setImage(new Image("sample/images/rocket0.png"));

                }


                background.setVelocity(0, spaceShip.getCurrentVelocity() / VELOCITY_SCALER);
                background.update(t);
                background.render(gc);
                sideViewGC.drawImage(rocket, width / 2, 211 - (spaceShip.getCurrentHeight() * 211) / 50000);
                sideViewGC.strokeLine(0, 300, width, 300);
                ship.render(gc);

                followingBackground.setVelocity(0, spaceShip.getCurrentVelocity() / VELOCITY_SCALER);
                followingBackground.update(t);
                followingBackground.render(gc);
                ship.render(gc);
            }
        }.start();

    }

    @Override
    public void update(Observable observable, Object o) {
        spaceShip = (SpaceShip) observable;
        Platform.runLater(() -> {
            updateData(spaceShip);

        });
    }

    private void updateData(SpaceShip spaceShip) {
        movementSeries.getData().add(new XYChart.Data<>(spaceShip.getCurrentVelocity(), spaceShip.getCurrentHeight()));
        if (spaceShip.getCurrentMass() < 1000500) {
            fuelUsageLabel.setText("Out of fuel");
            massLabel.setText("Mass: " + 1000000 + "g");


        }
    }
}