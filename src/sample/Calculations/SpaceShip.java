package sample.Calculations;


import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpaceShip extends Observable {
    private final double initialHeight = 50000;
    private final double initialMass = 2730140;
    private final double initialVelocity = -150;
    private double currentU = 0;
    private ResultsHandler resultsHandler;
    private ExecutorService executorService;

    public SpaceShip() {
        executorService = Executors.newSingleThreadExecutor();
        setCurrentU(currentU);
    }
    private void parametersChanged(){
        setChanged();
        notifyObservers();
    }


    public void setCurrentU(double currentU) {
        this.currentU = currentU;
        setCurrentParameters(currentU);

    }

    private void setCurrentParameters(double currentU){
        Movement movement = new Movement();
        movement.setU(currentU);

        executorService.submit(movement);
        resultsHandler = movement.getResultsHandler();
        parametersChanged();



    }


}
