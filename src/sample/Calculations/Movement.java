package sample.Calculations;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;
import org.apache.commons.math3.ode.nonstiff.EulerIntegrator;

import java.util.Arrays;

/**
 * Thread that calculates movement of a SpaceSheep Sprite in next second.
 */
public class Movement implements Runnable {

    //time to integrate
    private final double SIMULATION_TIME = 0.1;
    //step of integrate
    private double step = 0.01;
    //fuelUsage - parameter of burning rate
    private double fuelUsage = 0;

    private ResultsHandler resultsHandler;
    private double[] xStart;
    private double[] xStop;


    /**
     * Inits movement of the spaceship
     *
     * @param xStart
     */
    public Movement(double[] xStart) {
        this.xStart = xStart.clone();
        this.xStop = xStart.clone();
    }

    /**
     * @return ResultHandler, containing all values of last integration
     */
    public ResultsHandler getResultsHandler() {
        return resultsHandler;
    }

    /**
     * @param fuelUsage set Fuel Usage for integration
     */
    public void setFuelUsage(double fuelUsage) {
        this.fuelUsage = fuelUsage;
    }

    @Override
    public void run() {
        FirstOrderDifferentialEquations firstOrderDifferentialEquations = new MovementODE(fuelUsage);
        FirstOrderIntegrator integrator = new ClassicalRungeKuttaIntegrator(step);
        resultsHandler = new ResultsHandler();
        integrator.addStepHandler(resultsHandler);
        integrator.integrate(firstOrderDifferentialEquations, 0, xStart, SIMULATION_TIME, xStop);
    }
}
