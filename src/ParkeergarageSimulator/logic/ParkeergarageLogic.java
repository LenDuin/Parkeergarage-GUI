package ParkeergarageSimulator.logic;

import ParkeergarageSimulator.controller.RunController;
import ParkeergarageSimulator.exception.*;

public class ParkeergarageLogic extends AbstractModel implements Runnable {
    private int spots = 30;
    private int rows = 6;
    private int floors = 3;

    private Car[][][] cars;
    private int numberOfSteps;
    private boolean run = false;

    private Simulator simulator;

    public ParkeergarageLogic() {}

    public void setParkeergarage() {
        cars = new Car[floors][rows][spots];
        simulator = new Simulator(cars, spots, rows, floors);
        notifyViews();
    }

    public void doStep() throws ParkeergarageException {
        calculateParkeergarage();
        notifyViews();
    }

    public void doSteps(int numberOfSteps) throws ParkeergarageException {
        this.numberOfSteps = numberOfSteps;
        run = true;
        new Thread(this).start();
    }

    public void stopSteps() {
        run = false;
    }

    public Car[][][] getState() {
        return cars;
    }

    private void calculateParkeergarage() {
        simulator.tick();
        notifyViews();
    }

    public int getSpots() {
        return spots;
    }

    public int getRows() {
        return rows;
    }

    public int getFloors() {
        return floors;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfSteps && run; i++) {
            calculateParkeergarage();
            notifyViews();
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
