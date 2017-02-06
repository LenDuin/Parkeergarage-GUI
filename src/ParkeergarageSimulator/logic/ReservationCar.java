package ParkeergarageSimulator.logic;

import java.util.Random;
import java.awt.*;

/**
 * @author Samet Inci, I take no credit for the code, only for the addition of JavaDoc
 */
public class ReservationCar extends Car {
    private static final Color COLOR = Color.green;

    /**
     *
     * The constructor  of this class where it creates the cars randomly for people that reserved parking places.
     * The people that have parking pass must pay for the reserved spot and for how many hours it staid on the place
     *
     *
     *
     */

    public ReservationCar() {
        Random random = new Random();

        int stayMinutes = (int)(15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
        this.setChanceToEnter(0);
    }

    /**
     * this method changes the color of the cars that have reserved parking spots
     *
     *
     *@return change the colors of the cars that have reserved parking spots
     */
    public Color getColor(){
        return COLOR;
    }
}
