import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class StationPanel extends AnimatedPanel {
    private Image backgroundImage;
    private JProgressBar fuelGasGauge;
    private GasPump gasPump;
    private Car car;

    private int PUMP_X;
    private int PUMP_Y;
    private boolean isLeavingStation = false; // Flag to track if car is leaving

    public StationPanel() {
        super();

        backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/bg.png"))).getImage();
        fuelGasGauge = new JProgressBar(JProgressBar.VERTICAL);
        gasPump = new GasPump(100);

        // -- PROGRESS BAR SETUP --
        fuelGasGauge.setMinimum(0);
        fuelGasGauge.setMaximum(100);
        fuelGasGauge.setValue(75);

        this.setLayout(null);
        this.add(fuelGasGauge);
        this.setComponentZOrder(fuelGasGauge, 0);
    }

    public void init(){
        int barWidth = 24;
        int barHeight = 96;
        int xOffset = 76;
        PUMP_X = (getWidth()) / 2;
        PUMP_Y = (getHeight()) / 2;
        int xc = PUMP_X - xOffset - barWidth / 2;
        int yc = PUMP_Y - barHeight / 2;

        fuelGasGauge.setBounds(xc, yc, barWidth, barHeight);
    }

    public void updateFuelGauge(){
        fuelGasGauge.setValue((int)gasPump.getCurrentFuelLevel());
    }

    public void dispenseFuel(double amount){
        gasPump.dispenseFuel(amount);
        updateFuelGauge();
    }

    public void refill(){
        gasPump.refill();
        updateFuelGauge();
    }

    public Car getCar() {
        return car;
    }

    @Override
    public void update() {
        if (car == null) {
            // Create a new car if none exists
            car = new Car(1, 100);
            car.setLocation(getWidth(), getHeight() - car.getHeight());
            this.add(car);
            this.setComponentZOrder(car, 1);
            car.moveTowards(PUMP_X - car.getWidth() / 2, getHeight() - car.getHeight());
        } else {
            car.update();

            // Refuel the car if it's at the pump
            refuelCar();

            // Check if car should leave the station after refueling
            if (car.getFuelLevel() == car.getFuelCapacity() && !isLeavingStation) {
                leaveStation();
            }

            // Remove the car only when it has moved fully off-screen to the left
            if (isLeavingStation && car.getX() < -car.getWidth()) {
                this.remove(car);  // Remove the car from the panel
                car = null;         // Allow a new car to be created next update
                isLeavingStation = false;  // Reset the flag for the next car
            }
        }
    }

    public void refuelCar(){
        if (car != null && car.destination == null) {
            System.out.println("Car is at the pump and refueling.");
            car.refuel(gasPump);
            updateFuelGauge();
        }
    }

    public void leaveStation(){
        if (car != null) {
            System.out.println("Car is full and leaving the station.");
            car.moveTowards(-car.getWidth(), getHeight() - car.getHeight()); // Move the car to the left
            isLeavingStation = true; // Set the flag to indicate car is leaving
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}