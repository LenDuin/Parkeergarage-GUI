package ParkeergarageSimulator.logic;

import java.util.LinkedList;
import java.util.Queue;

public class CarQueue {
    private Queue<Car> queue = new LinkedList<>();

    /**
     * The method here it creates a array for cars that are waiting at the entrance.
     *
     * @return add 1 car when the boolean is true
     *
     */
    public boolean addCar(Car car) {
        return queue.add(car);
    }

    /**
     * This method removes cars from the queue.
     *
     * @return remove 1 car from the queue
     *
     */
    public Car removeCar() {
        return queue.poll();
    }

    /**
     * This method shows how much cars are waiting
     *
     * @return how much cars are still waiting to get in or out.
     *
     */
    public int carsInQueue(){
        return queue.size();
    }
}
