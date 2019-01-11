package sample.simulation;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import sample.Calculations.DataPackage;
import sample.Calculations.SpaceShip;
import sample.Controller;


public class SpaceShipUpdateService extends ScheduledService<DataPackage>{

    private volatile SpaceShip spaceShip;

    public SpaceShipUpdateService(SpaceShip spaceShip) {
        this.spaceShip = spaceShip;
    }

    @Override
    protected Task<DataPackage> createTask() {
        return new Task<DataPackage>() {
            @Override
            protected DataPackage call() throws Exception {
                    spaceShip.setCurrentFuelUsage(Controller.getFuelUsage());
                return  null;
            }
        };
    }

}
