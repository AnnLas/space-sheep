package sample.Calculations;

import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.sampling.StepHandler;
import org.apache.commons.math3.ode.sampling.StepInterpolator;

import java.util.ArrayList;
import java.util.Arrays;

public class ResultsHandler implements StepHandler {
    private ArrayList<Double> tValues = new ArrayList<>();
    private ArrayList<Double> hValues = new ArrayList<>();
    private ArrayList<Double> vValues = new ArrayList<>();
    private ArrayList<Double> mValues = new ArrayList<>();
    private double[] lastValues;


    @Override
    public void init(double t0, double[] y0, double t) {
    }

    public double[] getLastValues()  {
        return lastValues;
    }

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

    public double getLastHeightValue(){
        return getLastValues()[0];
    }

    public double getLastVelocityValue(){
        return getLastValues()[1];
    }

    public double getLastMassValue(){
        return getLastValues()[2];
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
}