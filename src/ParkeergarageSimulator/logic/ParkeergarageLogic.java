package ParkeergarageSimulator.logic;

import ParkeergarageSimulator.exception.*;
import java.util.*;

public class ParkeergarageLogic extends AbstractModel implements Runnable {
    private int spots;
    private boolean spotsIsSet;

    private int rows;
    private boolean rowsIsSet;

    private int floors;
    private boolean floorsIsSet;

    private boolean initRun;

    private int[][][] parkeergarage;
    private Random r;

    private int numberOfSteps;
    private boolean run;

    public ParkeergarageLogic() {
        spots = 60;
        rows = 3;
        floors = 3;
        spotsIsSet = false;
        rowsIsSet = false;
        floorsIsSet = false;
        r = new Random();
        run = false;
    }

    public void setParkeergarage(int floors, int rows, int spots) throws ParkeergarageException {
        parkeergarage = new int[floors][rows][spots];
        initRun = false;
    }

    public void doStep() throws ParkeergarageException {
        if (!floorsIsSet || !rowsIsSet || !spotsIsSet) {
            throw new ParkeergarageException("Parkeergarage not fully set, please check and fix");
        }
        if (!initRun) {
            throw new ParkeergarageException("plx run init ferst");
        }
        calculateParkeergarage();
        notifyViews();
    }

    public void doSteps(int numberOfSteps) throws ParkeergarageException {
        if (!floorsIsSet || !rowsIsSet || !spotsIsSet) {
            throw new ParkeergarageException("Parkeergarage not fully set, please check and fix");
        }
        if (!initRun) {
            throw new ParkeergarageException("plx run init ferst");
        }
        this.numberOfSteps = numberOfSteps;
        run = true;
        new Thread(this).start();
    }

    public void stopSteps() {
        run = false;
    }

    public int[][][] getState() {
        return parkeergarage;
    }

    public void randomInit() throws ParkeergarageException {
        if (!floorsIsSet || !rowsIsSet || !spotsIsSet) {
            throw new ParkeergarageException("Parkeergarage not fully set, plx fix");
        }
        for (int i = 0; i < floors; i++) {
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < rows; k++) {
                    if (r.nextInt() <= spots) {
                        parkeergarage[i][j][k] = 1;
                    }
                    else {
                        parkeergarage[i][j][k] = 0;
                    }
                    initRun = true;
                    notifyViews();
                }
            }
        }
    }

    private void calculateParkeergarage() {
        //TODO: Create this method
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfSteps && run; i++) {
            calculateParkeergarage();
            notifyViews();
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        run = false;
    }
}
