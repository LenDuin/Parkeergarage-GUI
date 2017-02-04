package ParkeergarageSimulator.logic;

import java.util.Random;
import java.awt.*;

public class ReservationCar extends Car {
    private static final Color COLOR = Color.green;

    public ReservationCar() {
        Random random = new Random();

        int stayMinutes = (int)(15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
        this.setChanceToEnter(0);
    }

    public Color getColor(){
        return COLOR;
    }
}
