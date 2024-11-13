import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Car extends AnimatedPanel {
    private int fuelLevel;
    private int fuelCapacity;
    private Image backgroundImage;

    Point destination = null;
    private double epsilon = 0.001;

    public Car(int type, int fuelCapacity) {
        super();

        this.fuelCapacity = fuelCapacity;
        // Randomly set the fuel level to a value between 0 and half of the fuel capacity
        this.fuelLevel = (int) (Math.random() * (fuelCapacity / 2));

        // Set background image based on the type of car
        this.backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/car" + type + "-nobg.png"))).getImage();

        // Scale the image to make the car smaller
        int newWidth = backgroundImage.getWidth(this) / 2;
        int newHeight = backgroundImage.getHeight(this) / 2;
        this.backgroundImage = backgroundImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        // Set background color to transparent
        this.setOpaque(false);
        // Set size of the car based on the background image
//        this.setSize(backgroundImage.getWidth(this), backgroundImage.getHeight(this));
        this.setSize(newWidth, newHeight);

    }

    @Override
    public void update() {
        // Set destination to null if the car has reached its destination
        if (destination != null) {
            double distance = Math.sqrt(Math.pow(destination.x - x, 2) + Math.pow(destination.y - y, 2));
            if (distance < epsilon) {
                x = destination.x;
                y = destination.y;
                destination = null;
            } else {
                // Move the car towards the destination
                double dx = destination.x - x;
                double dy = destination.y - y;
                double angle = Math.atan2(dy, dx);
                x += (int) (xSpeed * Math.cos(angle));
                y += (int) (ySpeed * Math.sin(angle));
            }
        }
        setLocation(x, y);
    }

    public void moveTowards(int x, int y) {
        destination = new Point(x, y);
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        this.x = x;
        this.y = y;
    }


    public void refuel(GasPump gasPump) {
        double fuelNeeded = fuelCapacity - fuelLevel;
        if (fuelNeeded > 0) {
            double fuelDispensed = Math.min(fuelNeeded, gasPump.getCurrentFuelLevel());
            fuelLevel += fuelDispensed;
            gasPump.dispenseFuel(fuelDispensed);
        }

    }

    public int getFuelLevel() {
        return fuelLevel;
    }

    public int getFuelCapacity() {
        return fuelCapacity;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}