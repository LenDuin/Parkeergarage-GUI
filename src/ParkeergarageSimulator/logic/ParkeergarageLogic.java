package ParkeergarageSimulator.logic;

import ParkeergarageSimulator.controller.RunController;
import ParkeergarageSimulator.exception.*;

/**
 * @author Lennard Duinkerken, I take no credit for the code, only for the addition of JavaDoc
 */
public class ParkeergarageLogic extends AbstractModel implements Runnable {
    private int spots = 30;
    private int rows = 6;
    private int floors = 3;

    private Car[][][] cars;
    private int numberOfSteps;
    private boolean run = false;

    private Simulator simulator;

    /**
     * an empty constructor (about as empty as my life :c) for parkeergarageLogic
     * this will do about as much as I do in weekends, which is nothing
     */
    public ParkeergarageLogic() {}

    /**
     * This is where the interesting stuff happens, cars are created, the simulator is initialized, and the views updated
     */
    public void setParkeergarage() {
        cars = new Car[floors][rows][spots];
        simulator = new Simulator(cars, spots, rows, floors);
        notifyViews();
    }

    /**
     * takes a single step through the simulator
     * @throws ParkeergarageException this is done because the calculate parkeergarage requires this
     */
    public void doStep() throws ParkeergarageException {
        calculateParkeergarage();
    }

    /**
     * basically the doSteps() but now multiple
     * @param numberOfSteps the amount of steps to take
     * @throws ParkeergarageException this is done because the calculate parkeergarage requires this
     */
    public void doSteps(int numberOfSteps) throws ParkeergarageException {
        this.numberOfSteps = numberOfSteps;
        run = true;
        new Thread(this).start();
    }

    /**
     * this will, quite obviously, stop the automatic stepping
     */
    public void stopSteps() {
        run = false;
    }

    /**
     * will return the state of each car
     * @return a value determining the state of a car, AD_HOC / PASS / RESERVATION
     */
    public Car[][][] getState() {
        return cars;
    }

    /**
     * this will actually do what the simulation is
     */
    private void calculateParkeergarage() {
        simulator.tick();
        notifyViews();
    }

    /**
     * [NOT USED]
     * in case you wat to know what the amount of spots is you can use this method to get them, yay :D
     * @return the amount of spots as an integer
     */
    public int getSpots() {
        return spots;
    }

    /**
     * [NOT USED]
     * in case you wat to know what the amount of rows is you can use this method to get them, yay :D
     * @return the amount of rows as an integer
     */
    public int getRows() {
        return rows;
    }

    /**
     * [NOT USED]
     * in case you wat to know what the amount of floors is you can use this method to get them, yay :D
     * @return the amount of floors as an integer
     */
    public int getFloors() {
        return floors;
    }

    /**
     * the run method will go through all steps automatically with a small delay to make everything run smoother
     */
    @Override
    public void run() {
        for (int i = 0; i < numberOfSteps && run; i++) {
            calculateParkeergarage();
            try {
                Thread.sleep(RunController.getDelay());
            }
            catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        run = false;
    }
}
