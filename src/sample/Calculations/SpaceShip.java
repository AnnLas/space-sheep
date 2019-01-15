package sample.Calculations;


import java.util.Observable;
import java.util.concurrent.*;

public class SpaceShip extends Observable {

    private double currentVelocity = -150;
    //currentHeight - curent height of the spaceship [m]
    private double currentHeight = 50000;
    //currentMass - current mass of the spaceship (with fuel) [g]
    private double currentMass = 2730140;
    // updated per one second
    private double currentFuelUsage = 0;
    // maximum speed of burning rate
    public static final double MAXIMUM_FUEL_USAGE = - 16500;
    // single change of fuel usage
    public static final double CHANGE_OF_FUEL_USAGE = 500;
    // amount of fuel
    public static final double SHIP_MASS = 1000000;
    // initial height
    public static final double INITIAL_HEIGHT = 50000;
    private boolean isEmptyFuelTank = false;
    private boolean hasLanded = false;
    private ExecutorService executorService;

    public SpaceShip() {
        executorService = Executors.newSingleThreadExecutor();
        setCurrentFuelUsage(currentFuelUsage);
    }

    private void parametersChanged(){
        setChanged();
        notifyObservers();
    }

    public double getCurrentFuelUsage() {
        return currentFuelUsage;
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
        Movement movement = new Movement(new double[]{currentHeight, currentVelocity, currentMass});
        movement.setFuelUsage(currentU);

        executorService.submit(movement);
        // wait interval of 100 ms. In this period thread should be terminated.
        // SHOULD BE CHANGED AS FAST AS POSSIBLE. ONLY FOR TESTS.
        executorService.awaitTermination(100, TimeUnit.MILLISECONDS);

        checkLimiters(movement);

        setChanged();
        notifyObservers(this);
    }

    private void checkLimiters(Movement movement) {
        if (movement.getResultsHandler()==null) return;
        currentHeight = movement.getResultsHandler().getLastHeightValue();
        currentVelocity = movement.getResultsHandler().getLastVelocityValue();
        currentMass = movement.getResultsHandler().getLastMassValue();
        if (currentMass <= SHIP_MASS ){
            isEmptyFuelTank = true;
            movement.getResultsHandler().getmValues().forEach(item->{
                if (item >= SHIP_MASS-CHANGE_OF_FUEL_USAGE&& item <= SHIP_MASS+CHANGE_OF_FUEL_USAGE){
                    if (Math.abs(item-SHIP_MASS)<Math.abs(currentMass-SHIP_MASS)) {
                        currentMass = item;
                        int index = movement.getResultsHandler().getmValues().indexOf(currentMass);
                        currentVelocity = movement.getResultsHandler().getvValues().get(index);
                        currentHeight = movement.getResultsHandler().gethValues().get(index);
                    }
                }
            });
        }
        if (currentHeight <0) {
            movement.getResultsHandler().gethValues().forEach(item -> {
                if (item>=0-50 && item <= 0+50){
                    if (Math.abs(item-50)<Math.abs(currentHeight -50)) {
                        currentHeight = item;
                        int index = movement.getResultsHandler().gethValues().indexOf(currentHeight);
                        currentVelocity = movement.getResultsHandler().getvValues().get(index);
                        hasLanded = true;
                    }
                }
            }
            );
        }
    }

    public double getCurrentVelocity() {
        return currentVelocity;
    }

    public double getCurrentHeight() {
        return currentHeight;
    }

    public double getCurrentMass() {
        return currentMass;
    }


    public boolean isHasLanded() {
        return hasLanded;
    }
}
