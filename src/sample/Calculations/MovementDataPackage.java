package sample.Calculations;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.Observable;
import java.util.Observer;

/**
 * Wrapper class should represent data about movement of sprite in next period of time
 */
public class MovementDataPackage implements Observer {

    private Label heightLabel;
    private Label velocityLabel;
    private Label massLabel;
    private Label fuelUsageLabel;
    private final String HEIGHT_DATA = "Height: ";
    private final String VELOCITY_DATA = "Velocity: ";
    private final String MASS_DATA = "Mass: ";
    private final String FUEL_USAGE_DATA = "Fuel usage: ";

    /**
     * Creates instance of the spaceship movement data package
     *
     * @param heightLabel    - part of UI with info about the height at which the ship is located
     * @param velocityLabel  - part of UI with info about the velocity of the spaceship
     * @param massLabel      - part of UI with info about the mass of the spaceship
     * @param fuelUsageLabel - part of UI with info about the fuel usage of the spaceship
     */
    public MovementDataPackage(Label heightLabel, Label velocityLabel, Label massLabel, Label fuelUsageLabel) {
        this.heightLabel = heightLabel;
        this.velocityLabel = velocityLabel;
        this.massLabel = massLabel;
        this.fuelUsageLabel = fuelUsageLabel;
    }

    @Override
    public void update(Observable o, Object arg) {
        SpaceShip spaceShip = (SpaceShip) o;
        Platform.runLater(() -> {
            heightLabel.setText(HEIGHT_DATA + spaceShip.getCurrentHeight() + "m");
            velocityLabel.setText(VELOCITY_DATA + spaceShip.getCurrentVelocity() + "m/s");
            massLabel.setText(MASS_DATA + spaceShip.getCurrentMass() + "g");
            fuelUsageLabel.setText(FUEL_USAGE_DATA + (spaceShip.getCurrentFuelUsage() + "g/s"));
        });
    }
}
