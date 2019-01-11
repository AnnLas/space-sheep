package sample.Calculations;


import java.util.Observable;
import java.util.concurrent.*;

public class SpaceShip extends Observable {

    private double velocityStart = -150;
    //heightStart - initial height of the spaceship [m]
    private double heightStart = 50000;
    //massStart - initial mass of the spaceship (with fuel) [g]
    private double massStart = 2730140;
    // updated per one second
    private double currentFuelUsage = 0;
    // maximum speed of burning rate
    public static final double MAXIMUM_FUEL_USAGE = 16500;
    // single change of fuel usage
    public static final double CHANGE_OF_FUEL_UASGE = 500;

    private ExecutorService executorService;

    public SpaceShip() {
        executorService = Executors.newSingleThreadExecutor();
        setCurrentFuelUsage(currentFuelUsage);
    }

    private void parametersChanged(){
        setChanged();
        notifyObservers();
    }

    /**
     * Set current burning rate (fuel Usage)
     * @param currentFuelUsage if param is higher than 0
     */
    public void setCurrentFuelUsage(double currentFuelUsage) {
        this.currentFuelUsage = currentFuelUsage;
        try {
            updateCurrentParameters(currentFuelUsage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateCurrentParameters(double currentU) throws InterruptedException {
        Movement movement = new Movement(new double[]{heightStart, velocityStart, massStart});
        movement.setFuelUsage(currentU);

        executorService.submit(movement);

            // wait interval of 100 ms. In this period thread should be terminated
            executorService.awaitTermination(100, TimeUnit.MILLISECONDS);

        heightStart = movement.getResultsHandler().getLastHeightValue();
        velocityStart = movement.getResultsHandler().getLastVelocityValue();
        massStart = movement.getResultsHandler().getLastMassValue();

        parametersChanged();
    }


}
