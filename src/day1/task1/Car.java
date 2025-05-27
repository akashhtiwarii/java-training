package day1.task1;

/**
 * Concrete class representing a Car. Inherits from BaseVehicle.
 */
public class Car extends BaseVehicle {

    /**
     * Constructs a Car with the given brand and speed.
     *
     * @param brand the brand of the car
     * @param speed the top speed of the car
     */
    public Car(String brand, int speed) {
        super(brand, speed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVehicleType() {
        return "Car";
    }

    /**
     * Opens the sunroof of the car.
     */
    public void openSunroof() {
        logger.info(getBrand() + " is opening the sunroof.");
    }

    /**
     * Plays music in the car.
     */
    public void playMusic() {
        logger.info(getBrand() + " is playing music.");
    }
}
