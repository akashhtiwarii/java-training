package day1.task1;

/**
 * Demonstrates the usage of Car and Bike classes.
 */
public class VehicleDemo {
    /**
     * Main method to run the demo.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Car car = new Car("Honda", 180);
        Bike bike = new Bike("Yamaha", 120);

        car.start();
        car.brake();
        car.openSunroof();
        car.playMusic();
        car.stop();

        bike.start();
        bike.kickStart();
        bike.popWheelie();
        bike.stop();
    }
}
