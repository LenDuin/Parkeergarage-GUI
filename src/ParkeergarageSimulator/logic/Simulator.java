package ParkeergarageSimulator.logic;

import ParkeergarageSimulator.controller.RunController;
import java.util.*;

public class Simulator {
    private static final String AD_HOC = "1";
    private static final String PASS = "2";
    private static final String RESERVATION = "3";

    private int weekDayArrivals = 100;
    private int weekendArrivals = 200;
    private int weekDayPassArrivals = 50;
    private int weekendPassArrivals = 0;
    private int weekDayResArrivals = 30;
    private int weekendResArrivals = 50;
    private int peakArrivals = 120;

    private int enterSpeed = 3;
    private int paymentSpeed = 7;
    private int exitSpeed = 5;

    private double chargePerMinute = 0.07;

    private CarQueue preResQueue;
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
    private static List<Double> dayVisitorsList;
    private static List<Double> dayRevenueList;

    private double revenue;

    public static List<Double> getDayRevenueList() {
        return dayRevenueList;
    }

    public Simulator(Car carsArr[][][], int spots, int rows, int floors) {
        preResQueue = new CarQueue();

        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();

        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        cars = carsArr;
        numberOfPlaces = spots;
        numberOfRows = rows;
        numberOfFloors = floors;
        numberOfOpenSpots = numberOfPlaces * numberOfRows * numberOfFloors;

        dayVisitorsList = new ArrayList<>();
        dayRevenueList = new ArrayList<>();
    }

    /**
     * Handles one tick of the simulator.
     */
    void tick() {
        advanceTime();
        handleExit();
        handleEntrance();
        handleCarTime();
        printInfo();
    }

    public static List<Double> getDayVisitors() {
        return dayVisitorsList;
    }

    /**
     * Prints statistics to the console. Debug function while views haven't been implemented.
     */
    private void printInfo() {
        if(minute == 0) {
            int prevHour = hour-1;
            if(prevHour < 0) {
                prevHour = 23;
            }
            System.out.println("Visitors between " + prevHour + " and " + hour + ": " + hourVisitors);
            /*System.out.println("Queue numbers (reg/pass - res): " + entranceCarQueue.carsInQueue() + " / " + entrancePassQueue.carsInQueue() + " " + preResQueue.carsInQueue());*/
            System.out.println("Number of open spots: " + numberOfOpenSpots);
            hourVisitors = 0;
            if(hour == 0) {
                int prevDay = day - 1;
                if(prevDay < 0) {
                    prevDay = 6;
                }
                System.out.println("Visitors on day " + prevDay + ": " + dayVisitors);
                dayVisitorsList.add((double)dayVisitors);
                dayRevenueList.add(revenue);
                dayVisitors = 0;
                if(day == 0) {
                    System.out.println("Visitors total: " + totalVisitors);
                    System.out.println("Revenue: " + revenue);
                }
            }
        }
    }

    /**
     * Changes the time that all cars will be staying by -1 minute to ensure they will eventually leave.
     */
    private void handleCarTime() {
        for (int floor = 0; floor < numberOfFloors; floor++) {
            for (int row = 0; row < numberOfRows; row++) {
                for (int place = 0; place < numberOfPlaces; place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    /**
     * Advances the time by one minute. In the case of overflow for minutes, hours or even days it resets that value
     * and adds one to the next step up. Biggest time element is a 7-day week, at the end the cycle restarts.
     */
    private void advanceTime() {
        // Advance the time by one minute. Shamelessly copied from original project
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
        updateClock();
    }

    /**
     * Updates the clock in our view.
     */
    private void updateClock() {
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

    /**
     * Handles the leaving of cars. Calls carsReadyToLeave() to prepare cars for leaving, then calls carsPaying() to
     * manage the payment queue, after which it calls carsLeaving() to manage the exit queue.
     */
    private void handleExit() {
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    /**
     * Handles the arrival and entrance of cars. carsArriving() manages the arrival of cars. carsTryingToEnqueue manages
     * the enqueueing of cars with a reservation, carsEntering manages the actual entering of cars in the queue.
     */
    private void handleEntrance() {
        carsArriving();
        carsTryingToEnQueue(preResQueue);
        carsEntering(entrancePassQueue);
        carsEntering(entranceCarQueue);
    }

    /**
     * Manages the number of arriving cars and gives this to the addArrivingCars method that manages the actual
     * enqueueing of cars.
     */
    private void carsArriving() {
        int numberOfCars;
        numberOfCars = getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);
        numberOfCars = getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);
        numberOfCars = getNumberOfCars(weekDayResArrivals, weekendResArrivals);
        addArrivingCars(numberOfCars, RESERVATION);
    }

    /**
     * Manages the movement from the reservation queue (cars that will potentially arrive) to the passQueue.
     * @param queue The "potential arrival" queue.
     */
    private void carsTryingToEnQueue(CarQueue queue) {
        Random random = new Random();
        Car car;
        int length = queue.carsInQueue();
        for (int i=0; i<length; i++) {
            car = queue.removeCar();
            if(carWantsToEnqueue(entrancePassQueue.carsInQueue())) {
                if((random.nextFloat()*100) < car.getChanceToEnter()) {
                    numberOfOpenSpots++;
                    entrancePassQueue.addCar(car);
                } else {
                    int chance = car.getChanceToEnter();
                    if (chance < 90) {
                        car.setChanceToEnter(chance+2);
                        queue.addCar(car);
                    } else {
                        numberOfOpenSpots++;
                    }
                }
            } else {
                numberOfOpenSpots++;
            }
        }
    }

    /**
     * Manages the entering of enqueued cars and adds to the visitor count. Also sets the fee the car needs to pay once
     * it leaves.
     * @param queue queue that needs to be managed.
     */
    private void carsEntering(CarQueue queue) {
        int i=0;
        Location freeLocation;
        int localEnterSpeed;
        localEnterSpeed = enterSpeed;
        if(day == 5 || day == 6) {
            localEnterSpeed *= 2;
        }
        while (queue.carsInQueue() > 0 && numberOfOpenSpots > 0 && i < localEnterSpeed) {
            Car car = queue.removeCar();
            if(car.getHasToPay() && day != 5 && day != 6) {
                freeLocation = getFirstFreeLocation(numReserved);
            } else {
                freeLocation = getFirstPassLocation();
            }
            car.setFee(chargePerMinute);
            setCarAt(freeLocation, car);
            dayVisitors++;
            hourVisitors++;
            totalVisitors++;
            i++;
        }
    }

    /**
     * Manages the leaving of cars, either by adding them to the payment queue or by letting them leave their spot. If
     * a car has to pay it is added to the paymentQueue, otherwise it is removed from its spot.
     * The getLeavingCars() method gives this method a queue of cars that are ready to leave which is then emptied. The
     * carLeaveSpot method removes a car from its spot and adds it to the exitQueue.
     * Edited to take a carQueue with all leaving cars instead of selecting the cars that have to leave in a loop.
     */
    private void carsReadyToLeave() {
        CarQueue queue = getLeavingCars();
        Car car = queue.removeCar();
        while (car != null) {
            if (car.getHasToPay()) {
                car.setIsPaying(true);
                paymentCarQueue.addCar(car);
            } else {
                carLeavesSpot(car);
            }
            car = queue.removeCar();
        }
    }

    /**
     * Handles the paying and removal of cars from their spots. handlePayment lets cars pay while carLeavesSpot manages
     * the removal from a car from their spot.
     */
    private void carsPaying() {
        int i=0;
        while (paymentCarQueue.carsInQueue() > 0 && i < paymentSpeed) {
            Car car = paymentCarQueue.removeCar();
            handlePayment(car);
            carLeavesSpot(car);
            i++;
        }
    }

    /**
     * Adds the fee that is paid by the car to the total revenue. $$$!
     * @param car   The car that has to pay.
     */
    private void handlePayment(Car car) {
        revenue += car.getFee();
    }

    /**
     * Removes cars from the exitCarQueue up to the maximum number of cars that can leave in a minute.
     */
    private void carsLeaving() {
        int i=0;
        while(exitCarQueue.carsInQueue()>0 && i < exitSpeed) {
            exitCarQueue.removeCar();
            i++;
        }
    }

    /**
     * Gives the number of cars that arrives at a given moment. Uses a trigonometric function to simulate the ebb and
     * flood of visitors every day with several small additions to account for certain peak moments.
     * @param weekDay   Cars that arrive on average each hour on a weekday.
     * @param weekend   Cars that arrive on average each hour in the weekend.
     * @return  The number of cars that arrives during the given minute.
     */
    private int getNumberOfCars(int weekDay, int weekend) {
        Random random = new Random();
        int toAdd = 0;

        int averageNumberOfCarsPerHour = day < 5 ? weekDay : weekend;
        double x = hour + (minute * 0.0167);
        double modifier = Math.sin((x/12 - 0.625)*Math.PI);

        double numberOfCarsPerHour;
        if(day==3) {
            modifier = modifier * 1.4;
        }
        if((day == 4 || day == 5 ) && (hour > 20 && hour < 22)) {
            x -= 20;
            toAdd = (int) (Math.sin((x/2)*Math.PI)) * peakArrivals;
        }
        if(day == 6 && (hour > 14 && hour < 16)) {
            x-=14;
            toAdd = (int) (Math.sin((x/2)*Math.PI)) * peakArrivals;
        }
        numberOfCarsPerHour = (modifier * averageNumberOfCarsPerHour * 0.5) + (averageNumberOfCarsPerHour * 0.6) + toAdd;

        double standardDeviation = numberOfCarsPerHour * 0.2;
        numberOfCarsPerHour += random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);
    }

    /**
     * Adds arriving cars to the arrival queues, provided the queue is not longer than 6 spots (because screw being 7th
     * in line). WORK IN PROGRESS.
     * @param numberOfCars  Number of cars that have arrived at the parking garage, ready to queue up.
     * @param type          The type (AD_HOC, PASS or RESERVATION) to determine which queue they need to join etc.
     */
    private void addArrivingCars(int numberOfCars, String type) {
        switch(type) {
            case AD_HOC:
                for (int i=0; i<numberOfCars; i++) {
                    if(carWantsToEnqueue(entranceCarQueue.carsInQueue())) {
                        entranceCarQueue.addCar(new AdHocCar());
                    }
                }
                break;
            case PASS:
                for (int i=0; i<numberOfCars; i++) {
                    if(carWantsToEnqueue(entrancePassQueue.carsInQueue())) {
                        entrancePassQueue.addCar(new ParkingPassCar());
                    }
                }
                break;
            case RESERVATION:
                for (int i=0; i<numberOfCars; i++) {
                    preResQueue.addCar(new ReservationCar());
                    numberOfOpenSpots--;
                }
                break;
        }
    }

    /**
     * Returns a boolean to determine whether a car wants to enqueue.
     * @param length    Length of the pre-existing queue.
     * @return          TRUE if a car wants to enqueue, FALSE if they don't.
     */
    private boolean carWantsToEnqueue(int length) {
        if (length <= 4) {
            return true;
        } else {
           Random random = new Random();
           double chance = 1/Math.pow(2,(length-4));
           return (chance <= random.nextDouble());
        }
    }

    /**
     * Removes a car from a spot and adds it to the exitQueue.
     * @param car   The car that is to be removed.
     */
    private void carLeavesSpot(Car car) {
        removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

    /**
     * Checks if a car exists in the given location and returns it.
     * @param location  Location where to look for a car.
     * @return          Either a car or null (if the location does not contain a car).
     */
    private Car getCarAt(Location location) {
        if(!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    /**
     * Places car at given location.
     * @param location  Location where to place car.
     * @param car       Car that is to be placed.
     * @return          Returns true on success, false on failure.
     */
    private boolean setCarAt(Location location, Car car) {
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

    /**
     * Returns the car that is removed from a given location.
     * @param location  Location to remove car from.
     * @return          Car that has been removed.
     */
    private Car removeCarAt(Location location) {
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

    /**
     * Returns the first location where a pass holder can park.
     * @return  First valid (empty) location. Null on failure.
     */
    private Location getFirstPassLocation() {
        for (int floor = 0; floor < numberOfFloors; floor++) {
            for (int row = 0; row < numberOfRows; row++) {
                for (int place = 0; place < numberOfPlaces; place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns first location where any customer can park, skips over the first numRes locations (numRes = number of
     * reserved spots).
     * @param numRes    Number of reserved spots that are to be skipped over.
     * @return          Returns the first valid (empty) location. Null on failure.
     */
    private Location getFirstFreeLocation(int numRes) {
        int i = 0;
        for (int floor = 0; floor < numberOfFloors; floor++) {
            for (int row = 0; row < numberOfRows; row++) {
                for (int place = 0; place < numberOfPlaces; place++) {
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

    /**
     * Returns a queue of cars whose minutes left has reached zero, sorted by first-searched location.
     * @return  Queue of cars whose minutes left has reached zero.
     */
    private CarQueue getLeavingCars() {
        CarQueue queue = new CarQueue();

        for (int floor = 0; floor < numberOfFloors; floor++) {
            for (int row = 0; row < numberOfRows; row++) {
                for (int place = 0; place < numberOfPlaces; place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        queue.addCar(car);
                    }
                }
            }
        }
        return queue;
    }

    /**
     * Returns if a location is within the parking garage.
     * @param location  A certain location.
     * @return          True if location is within garage, false if location is not.
     */
    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        return !(floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces);
    }

    int getMinute() {
        return minute;
    }

    int getHour() {
        return hour;
    }

    int getDay() {
        return day;
    }

}