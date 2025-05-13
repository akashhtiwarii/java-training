package day1.task1;

import java.util.logging.Logger;

/**
 * Abstract class providing a base implementation of the Vehicle interface.
 * Encapsulates shared state and behavior for all vehicle types.
 */
public abstract class BaseVehicle implements Vehicle {
    /**
     * Shared logger instance for subclasses.
     */
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    private final String brand;
    private final int speed;

    /**
     * Constructor for BaseVehicle.
     *
     * @param brand the brand of the vehicle
     * @param speed the top speed of the vehicle
     */
    protected BaseVehicle(String brand, int speed) {
        this.brand = brand;
        this.speed = speed;
    }

    /**
     * Returns the brand of the vehicle.
     *
     * @return brand name
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Returns the top speed of the vehicle.
     *
     * @return speed in km/h
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        logger.info(brand + " is starting...");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        logger.info(brand + " is stopping...");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void brake() {
        logger.info(brand + " is braking...");
    }

    /**
     * Returns the specific type of the vehicle (e.g., Car, Bike).
     *
     * @return type of vehicle
     */
    public abstract String getVehicleType();
}
