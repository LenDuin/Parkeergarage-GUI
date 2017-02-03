package ParkeergarageSimulator.logic;

import java.awt.*;

public abstract class Car {
    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;
    private double fee;

    /**
     * Constructor for objects of class Car
     */
    public Car() {}

    /**
     * Location getter for the car, determines where
     * the car is positioned in the parking lot
     * @return location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Location setter for the car, sets the location for the car,
     * say, when a car has to be replaced
     * @param location The location has to be the class known as Location,
     *                 this will assign the car a floor, row, and place
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * The car has a certain amount of time it will stay in a
     * certain location, after getMinutesLeft() == 0, the car will move
     * @return minutesLeft minutes the car has let
     */
    public int getMinutesLeft() {
        return minutesLeft;
    }

    /**
     * Sets the minutes a car is allowed in one location
     * @param minutesLeft an integer for how many minutes a car will
     *                    stay in a position
     */
    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    /**
     * A car has to pay when it's leaving so this method will check
     * whether a car is paying.
     * @return boolean with paying yes/no
     */
    public boolean getIsPaying() {
        return isPaying;
    }

    /**
     * Determines whether a car is paying or not, and by extension
     * if it's allowed to leave
     * @param isPaying a boolean to determine the payment state of a car
     */
    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    /**
     * Cardholders don't have to pay, this can be used to determine
     * whether a car is a cardholder, either that, or if the car
     * has already payed (if that's the case the code's broken)
     * @return returns whether a car has to pay or not
     */
    public boolean getHasToPay() {
        return hasToPay;
    }

    /**
     * Will set what is getted in the getHasToPay.
     * @param hasToPay a boolean to set if a car has to pay or not
     */
    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double chargingRate) {
        fee = minutesLeft * chargingRate;
    }

    /**
     * Ticks down the amount of minutes a car can stay
     * (not the most optimal way of doing this, by far)
     */
    public void tick() {
        minutesLeft--;
    }

    public abstract Color getColor();
}