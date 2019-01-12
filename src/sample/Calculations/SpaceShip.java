package sample.Calculations;


import java.util.Comparator;
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
    public static final double MAXIMUM_FUEL_USAGE = - 16500;
    // single change of fuel usage
    public static final double CHANGE_OF_FUEL_USAGE = 500;
    // amount of fuel
    public static final double SHIP_MASS = 1000000;
    private boolean isEmptyFuelTank = false;
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
        if (!isEmptyFuelTank)
        this.currentFuelUsage = currentFuelUsage;
        else currentFuelUsage = 0;
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
        // wait interval of 100 ms. In this period thread should be terminated.
        // SHOULD BE CHANGED AS FAST AS POSSIBLE. ONLY FOR TESTS.
        executorService.awaitTermination(100, TimeUnit.MILLISECONDS);

        heightStart = movement.getResultsHandler().getLastHeightValue();
        velocityStart = movement.getResultsHandler().getLastVelocityValue();
        massStart = movement.getResultsHandler().getLastMassValue();
        if (massStart <= SHIP_MASS ){
            isEmptyFuelTank = true;
            movement.getResultsHandler().getmValues().forEach(item->{
                if (item >= SHIP_MASS-CHANGE_OF_FUEL_USAGE&& item <= SHIP_MASS+CHANGE_OF_FUEL_USAGE){
                    if (Math.abs(item-SHIP_MASS)<Math.abs(massStart-SHIP_MASS)) {
                        massStart = item;
                        int index = movement.getResultsHandler().getmValues().indexOf(massStart);
                        velocityStart = movement.getResultsHandler().getvValues().get(index);
                        heightStart = movement.getResultsHandler().gethValues().get(index);
                    }
                }
            });
        }
        if (heightStart<0) {
            movement.getResultsHandler().gethValues().forEach(item -> {
                if (item>=0-50 && item <= 0+50){
                    if (Math.abs(item-50)<Math.abs(heightStart-50)) {
                        heightStart = item;
                        int index = movement.getResultsHandler().gethValues().indexOf(heightStart);
                        velocityStart = movement.getResultsHandler().getvValues().get(index);
                    }
                }
            }
            );
        }
        parametersChanged();
    }

    public double getVelocityStart() {
        return velocityStart;
    }

    public double getHeightStart() {
        return heightStart;
    }

    public double getMassStart() {
        return massStart;
    }
}
