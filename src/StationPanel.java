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
        // Set the Z order of the progress bar to 0
        this.setComponentZOrder(fuelGasGauge, 0);
        // ------------------------------------------------------
    }

    public void init(){
        // Get the center of the screen
        int barWidth = 24;
        int barHeight = 96;
        int xOffset = 76;
        PUMP_X = (getWidth()) / 2;
        PUMP_Y = (getHeight()) / 2;
        int xc = PUMP_X - xOffset - barWidth / 2;
        int yc = PUMP_Y - barHeight / 2;

        // Set the bounds of the progress bar
        fuelGasGauge.setBounds(xc, yc, barWidth, barHeight);
    }

    // -- visually represent the fuel level of the pump on a fuel gauge, like a progress bar. --
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
        // Check if there is no car, then create a new car
        if (car == null) {
            // Create a new car (with a random type)
            car = new Car(1, 100);
            // Set the location of the car outside the screen on the right (same height as the pump)
            car.setLocation(getWidth() / 2, PUMP_Y - getHeight() / 2);
            // Add the car to the panel
            this.remove(fuelGasGauge);
            this.add(car);
            // Set the Z order of the car to 1
            this.setComponentZOrder(car, 1);

            // Start the car animation
            car.moveTowards(PUMP_X - getWidth() / 2, PUMP_Y - getHeight() / 2);
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