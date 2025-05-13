package day1.task1;

/**
 * Concrete class representing a Bike. Inherits from BaseVehicle.
 */
public class Bike extends BaseVehicle {

    /**
     * Constructs a Bike with the given brand and speed.
     *
     * @param brand the brand of the bike
     * @param speed the top speed of the bike
     */
    public Bike(String brand, int speed) {
        super(brand, speed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVehicleType() {
        return "Bike";
    }

    /**
     * Performs a wheelie on the bike.
     */
    public void popWheelie() {
        logger.info(getBrand() + " is popping a wheelie!");
    }

    /**
     * Kick-starts the bike.
     */
    public void kickStart() {
        logger.info(getBrand() + " is kick-starting.");
    }
}
