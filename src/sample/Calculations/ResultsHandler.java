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

    @Override
    public void init(double t0, double[] y0, double t) {
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
        System.out.println(t + " " + Arrays.toString(x));
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