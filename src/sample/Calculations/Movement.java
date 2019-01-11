package sample.Calculations;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.EulerIntegrator;

import java.util.Arrays;

public class Movement implements Runnable{

    //data about movement in next period
    private double simulationTime = 1;

    private double step = 0.01;
    //vStart - initial speed of the spaceship [m/s]

    private double fuelUsage = 0;

    private ResultsHandler resultsHandler; //static to preserve last movement
    private double[] xStart; //static to preserve next movement
    private double[] xStop;  //static to preserve next movement


    public Movement(double[] xStart) {
        this.xStart = xStart.clone();
        this.xStop = xStart.clone();
    }


    public ResultsHandler getResultsHandler() {
        return resultsHandler;
    }


    public void setFuelUsage(double fuelUsage) {
        this.fuelUsage = fuelUsage;
    }

    @Override
    public void run() {
        FirstOrderDifferentialEquations firstOrderDifferentialEquations = new MovementODE(fuelUsage);
        FirstOrderIntegrator integrator = new EulerIntegrator(step);
        resultsHandler = new ResultsHandler();
        integrator.addStepHandler(resultsHandler);
        integrator.integrate(firstOrderDifferentialEquations, 0, xStart, simulationTime, xStop);
    }
}
