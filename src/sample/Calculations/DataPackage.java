package sample.Calculations;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Wrapper class should represent data about movement of sprite in next period of time
 */
public class DataPackage implements Observer {
    private ArrayList<Double> tValues;
    private ArrayList<Double> hValues;
    private ArrayList<Double> vValues;
    private ArrayList<Double> mValues;
    private Label heightLabel;
    private Label velocityLabel;
    private Label massLabel;

    public DataPackage(Label heightLabel, Label velocityLabel, Label massLabel) {
        this.heightLabel = heightLabel;
        this.velocityLabel = velocityLabel;
        this.massLabel = massLabel;
    }

    public ArrayList<Double> gettValues() {
        return tValues;
    }

    public ArrayList<Double> gethValues() {
        return hValues;
    }

    public ArrayList<Double> getvValues() {
        return vValues;
    }

    public ArrayList<Double> getmValues() {
        return mValues;
    }

    @Override
    public void update(Observable o, Object arg) {
        SpaceShip spaceShip = (SpaceShip) o;
        Platform.runLater(()->{
            heightLabel.setText(String.valueOf(spaceShip.getHeightStart()));
            velocityLabel.setText(String.valueOf(spaceShip.getVelocityStart()));
            massLabel.setText(String.valueOf(spaceShip.getMassStart()));
        });
    }
}
