package sample.simulation;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import sample.Calculations.MovementDataPackage;
import sample.Calculations.SpaceShip;
import sample.Controller;

/**
 * Updates SpaceShip parameters after a period of time
 */
public class SpaceShipUpdateService extends ScheduledService<MovementDataPackage>{

    private volatile SpaceShip spaceShip;

    public SpaceShipUpdateService(SpaceShip spaceShip) {
        this.spaceShip = spaceShip;
    }

    @Override
    protected Task<MovementDataPackage> createTask() {
        return new Task<MovementDataPackage>() {
            @Override
            protected MovementDataPackage call() throws Exception {
                    if (spaceShip.getCurrentHeight()<0) SpaceShipUpdateService.this.cancel();
                    spaceShip.setCurrentFuelUsage(Controller.getFuelUsage());
                return  null;
            }
        };
    }

}
