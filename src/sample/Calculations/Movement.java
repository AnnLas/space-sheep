package sample.Calculations;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.EulerIntegrator;

public class Movement implements Runnable{

    private double simulationTime = 0.2;

    private double step = 0.01;
    //vStart - initial speed of the spaceship [m/s]
    private double vStart = -150;
    //hStart - initial height of the spaceship [m]
    private double hStart = 50000;
    //mStart - initial mass of the spaceship (with fuel) [g]
    private double mStart = 2730140;
    private double  u = 0;

    private ResultsHandler resultsHandler;
    private double[] xStart;
    private double[] xStop;

    public ResultsHandler getResultsHandler() {
        return resultsHandler;
    }

    public void setU(double u) {
        this.u = u;
    }

    @Override
    public void run() {
        FirstOrderDifferentialEquations firstOrderDifferentialEquations = new MovementODE(u);
        FirstOrderIntegrator integrator = new EulerIntegrator(step);
        if (resultsHandler ==null) {
            resultsHandler = new ResultsHandler();
            xStart = new double[]{vStart, hStart, mStart};
            xStop = new double[]{vStart, hStart, mStart};
        }
        else {
            xStart = xStop;

        }
        integrator.addStepHandler(resultsHandler);
        integrator.integrate(firstOrderDifferentialEquations, 0, xStart, simulationTime, xStop);
    }
}
