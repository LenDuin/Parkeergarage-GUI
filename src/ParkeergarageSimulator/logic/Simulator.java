package ParkeergarageSimulator.logic;

import ParkeergarageSimulator.controller.RunController;

import java.util.*;
import static java.lang.System.*;

public class Simulator {
    private static final String AD_HOC = "1";
    private static final String PASS = "2";

    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;

    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    private Car[][][] cars;

    private int numReserved = RunController.getReservations();

    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;

    private int hourVisitors;
    private int dayVisitors;
    private int totalVisitors;

    private int weekDayArrivals = 100;
    private int weekendArrivals = 500;
    private int weekDayPassArrivals = 50;
    private int weekendPassArrivals = 5;

    private int enterSpeed = 3;
    private int paymentSpeed = 7;
    private int exitSpeed = 5;

    private double chargePerMinute = 0.07;
    private double revenue;

    public Simulator(Car carsArr[][][], int spots, int rows, int floors) {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        cars = carsArr;
        numberOfPlaces = spots;
        numberOfRows = rows;
        numberOfFloors = floors;
        numberOfOpenSpots = numberOfPlaces * numberOfRows * numberOfFloors;
    }

    public void tick() {
        advanceTime();
        updateClock();
        handlePayment();
        handleExit();
        handleEntrance();
        handleCarTime();
        printInfo();
    }

    public void updateClock() {
        int minute = getMinute();
        int hour = getHour();
        int dayNum = getDay();
        String dayTxt = "maandag";
        switch (dayNum) {
            case 0:
                dayTxt = "maandag";
                break;
            case 1:
                dayTxt = "dinsdag";
                break;
            case 2:
                dayTxt = "woensdag";
                break;
            case 3:
                dayTxt = "donderdag";
                break;
            case 4:
                dayTxt = "vrijdag";
                break;
            case 5:
                dayTxt = "zaterdag";
                break;
            case 6:
                dayTxt = "zondag";
                break;
        }
        RunController.setClock(dayTxt + ", " + hour + ":" + minute);
    }

    private void printInfo() {
        if(minute == 0) {
            int prevHour = hour-1;
            if(prevHour < 0) {
                prevHour = 23;
            }
//            System.out.println("Visitors between " + prevHour + " and " + hour + ": " + hourVisitors);
            hourVisitors = 0;
            if(hour == 0) {
                int prevDay = day - 1;
                if(prevDay < 0) {
                    prevDay = 6;
                }
//                System.out.println("Visitors on day " + prevDay + ": " + dayVisitors);
                dayVisitors = 0;
                if(day == 0) {
//                    System.out.println("Visitors in the last week: " + totalVisitors);
                    totalVisitors = 0;
                }
            }
        }
    }

    private void handlePayment() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.setFee(chargePerMinute);
                    }
                }
            }
        }
    }

    private void handleCarTime() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    private void advanceTime() {
        // Advance the time by one minute. Shamelessly copied from original project
//        System.out.println(hour + ":" + minute + ", " + day);
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }
    }

    private void handleExit() {
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    private void handleEntrance() {
        carsArriving();
        carsEntering(entrancePassQueue);
        carsEntering(entranceCarQueue);
    }

    private void carsArriving() {
        int numberOfCars = getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);
        numberOfCars = getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);
    }

    private void carsEntering(CarQueue queue) {
        int i=0;
        Location freeLocation;
        while (queue.carsInQueue() > 0 && getNumberOfOpenSpots() > 0 && i < enterSpeed) {
            Car car = queue.removeCar();
            if(car.getHasToPay()) {
                freeLocation = getFirstFreeLocation(numReserved);
            } else {
                freeLocation = getFirstPassLocation();
            }
            setCarAt(freeLocation, car);
            dayVisitors++;
            hourVisitors++;
            totalVisitors++;
            i++;
        }
    }

    private void carsReadyToLeave() {
        Car car = getFirstLeavingCar();
        while (car!=null) {
            if (car.getHasToPay()) {
                car.setIsPaying(true);
                paymentCarQueue.addCar(car);
            } else {
                carLeavesSpot(car);
            }
            car = getFirstLeavingCar();
        }
    }

    private void carsPaying() {
        int i=0;
        while (paymentCarQueue.carsInQueue() > 0 && i < paymentSpeed) {
            Car car = paymentCarQueue.removeCar();
            if (car.getFee() <= 0) {
                carLeavesSpot(car);
            }
            i++;
        }
    }

    private void carsLeaving() {
        int i=0;
        while(exitCarQueue.carsInQueue()>0 && i < exitSpeed) {
            exitCarQueue.removeCar();
            i++;
        }
    }

    private int getNumberOfCars(int weekDay, int weekend) {
        Random random = new Random();

        int averageNumberOfCarsPerHour = day < 5 ? weekDay : weekend;
        double x = hour + (minute * 0.0167);
        double modifier = Math.sin((x/12 - 0.625)*Math.PI);

        double numberOfCarsPerHour = (modifier * averageNumberOfCarsPerHour * 0.5) + (averageNumberOfCarsPerHour * 0.6);

        double standardDeviation = numberOfCarsPerHour * 0.2;
        numberOfCarsPerHour += random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);
    }

    private void addArrivingCars(int numberOfCars, String type) {
        switch(type) {
            case AD_HOC:
                for (int i=0; i<numberOfCars; i++) {
                    entranceCarQueue.addCar(new AdHocCar());
                }
                break;
            case PASS:
                for (int i=0; i<numberOfCars; i++) {
                    entrancePassQueue.addCar(new ParkingPassCar());
                }
                break;
        }
    }

    private void carLeavesSpot(Car car) {
        removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public int getNumberOfOpenSpots() {
        return numberOfOpenSpots;
    }

    public Car getCarAt(Location location) {
        if(!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
            return true;
        }
        return false;
    }

    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
        return car;
    }

    public Location getFirstPassLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }
        return null;
    }

    public Location getFirstFreeLocation(int numRes) {
        int i = 0;
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    if(i>=numRes) {
                        Location location = new Location(floor, row, place);
                        if(getCarAt(location) == null) {
                            return location;
                        }
                    }
                    i++;
                }
            }
        }
        return null;
    }

    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        return !(floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces);
    }

    public int getDayVisitors() {
        return dayVisitors;
    }

    public int getHourVisitors() {
        return hourVisitors;
    }

    public int getTotalVisitors() {
        return totalVisitors;
    }

    public int getMinute() {
        return minute;
    }

    public int getHour() {
        return hour;
    }

    public int getDay() {
        return day;
    }

}