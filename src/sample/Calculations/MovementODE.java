package sample.Calculations;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

public class MovementODE implements FirstOrderDifferentialEquations {


    //g - gravitational acceleration near the moon [m/s^2]
    private static final double g = 1.63;
    //k - gas outlet velocity [m/s]
    private static final double k = 636;
    //burning rate
    private final double fuelUsage;


    @Override
    public int getDimension() {
        return 3;
    }

    public MovementODE(double fuelUsage) {
        this.fuelUsage = fuelUsage;
    }

    @Override
    public void computeDerivatives(double t, double[] x, double[] dxdt) throws MaxCountExceededException, DimensionMismatchException {
        //dxdt[0] - velocity of the ship at the moment of time
        dxdt[0] = x[1];
        //dxdt[1] - acceleration of the ship at the moment of time
        dxdt[1] = -g - k * fuelUsage / x[2];
        //dxdt[2] - weight of the ship at the moment (ship & fuel)
        dxdt[2] = fuelUsage;
    }
}
