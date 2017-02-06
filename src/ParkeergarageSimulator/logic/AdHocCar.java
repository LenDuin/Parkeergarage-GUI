package ParkeergarageSimulator.logic;

import java.util.Random;
import java.awt.*;

public class AdHocCar extends Car {
    private static final Color COLOR = Color.red;

    /**
     * The constructor of this class.
     * in this class the normal cars are created.
     * it is shown with the color red in the frame
     *
     */
    public AdHocCar() {
        Random random = new Random();
        int stayMinutes = (int)(15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
        this.setChanceToEnter(100);
    }
    /**
     * This method changes the cars colors red
     *
     * @return The color of the cars
     */
    public Color getColor(){
        return COLOR;
    }

}
