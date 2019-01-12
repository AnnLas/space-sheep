package sample.Calculations;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.ArrayList;
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
    public MovementDataPackage(Label heightLabel, Label velocityLabel, Label massLabel, Label fuelUsageLabel) {
        this.heightLabel = heightLabel;
        this.velocityLabel = velocityLabel;
        this.massLabel = massLabel;
        this.fuelUsageLabel = fuelUsageLabel;
    }

    @Override
    public void update(Observable o, Object arg) {
        SpaceShip spaceShip = (SpaceShip) o;
        Platform.runLater(()->{
            heightLabel.setText(HEIGHT_DATA + String.valueOf(spaceShip.getHeightStart()));
            velocityLabel.setText(VELOCITY_DATA + String.valueOf(spaceShip.getVelocityStart()));
            massLabel.setText(MASS_DATA + String.valueOf(spaceShip.getMassStart()));
            fuelUsageLabel.setText( FUEL_USAGE_DATA + String.valueOf(spaceShip.getCurrentFuelUsage()));
        });
    }
}
