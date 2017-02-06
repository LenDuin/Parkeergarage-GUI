package ParkeergarageSimulator.logic;

import java.util.Random;
import java.awt.*;

public class ParkingPassCar extends Car {
    private static final Color COLOR=Color.blue;

    /**
     * The constructor  of this class where it creates the cars randomly for people with parking pass.
     * The people that have parking pass must pay daily and for how many hours it staid on the place
     *
     *
     *
     */
    public ParkingPassCar() {
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
        this.setChanceToEnter(100);
    }
    /**
     * The method here changes the colors of the cars that have parking pass
     *
     * @return the color of the cars that have parking pass
     *
     */
    public Color getColor(){
        return COLOR;
    }
}