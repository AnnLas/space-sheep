package sample.Calculations;

import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.sampling.StepHandler;
import org.apache.commons.math3.ode.sampling.StepInterpolator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Store all results of the calculations.
 */
public class ResultsHandler implements StepHandler {
    private ArrayList<Double> tValues = new ArrayList<>();
    private ArrayList<Double> hValues = new ArrayList<>();
    private ArrayList<Double> vValues = new ArrayList<>();
    private ArrayList<Double> mValues = new ArrayList<>();
    private double[] lastValues;

    /**
     * Initialize step handler at the start of an ODE integration.
     * @param t0- start value of the independent time variable
     * @param y0  array containing the start value of the state vector
     * @param t  - target time for the integration
     */
    @Override
    public void init(double t0, double[] y0, double t) {
    }

    /**
     * Returns last values of the calculations
     * @return - last values of the calculations
     */
    public double[] getLastValues()  {
        return lastValues;
    }

    /**
     * Handle the last accepted step
     * @param interpolator  interpolator for the last accepted step
     * @param isLast - true if the step is the last one
     * @throws MaxCountExceededException
     */
    @Override
    public void handleStep(StepInterpolator interpolator, boolean isLast) throws
            MaxCountExceededException {
        double t = interpolator.getCurrentTime();
        double[] x = interpolator.getInterpolatedState();
        tValues.add(t);
        hValues.add(x[0]);
        vValues.add(x[1]);
        mValues.add(x[2]);

        if (isLast) {
            lastValues = x;
            System.out.println(t + " " + Arrays.toString(x));
        }
    }

    /**
     * Returns last height at which the ship were located
     * @return - last height at which the ship were located [m]
     */
    public double getLastHeightValue(){
        return getLastValues()[0];
    }

    /**
     * Returns last velocity of the spaceship
     * @return last spaceship velocity [m.s]
     */
    public double getLastVelocityValue(){
        return getLastValues()[1];
    }

    /**
     * Returns last mass of the spaceship
     * @return last mass of the spaceship [g]
     */
    public double getLastMassValue(){
        return getLastValues()[2];
    }

    /**
     * Returns list of spaceship h location
     * @return list of spaceship h location
     */
    public ArrayList<Double> gethValues() {
        return hValues;
    }
    /**
     * Returns list of spaceship velocities
     * @return list of spaceship velocities
     */
    public ArrayList<Double> getvValues() {
        return vValues;
    }
    /**
     * Returns list of spaceship weights
     * @return list of spaceship weights
     */
    public ArrayList<Double> getmValues() {
        return mValues;
    }
}