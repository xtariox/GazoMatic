package com.gazomatic.station;

import com.gazomatic.IAnimated;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Car extends JPanel implements IAnimated {
    private int fuelLevel;
    private final int fuelCapacity;
    private Image backgroundImage;

    private double speed = 10;
    private static final double MAX_SPEED = 10;
    private static final double MIN_SPEED = 1;
    private int x = 0;
    private int y = 0;
    private Point destination = null;
    private Point oldLocation = new Point(x, y);
    private boolean isLeaving = false; // Flag to track if car is leaving (will be set to true when after the first stop)

    public Car(int type, int fuelCapacity, int fuelLevel) {
        this.fuelCapacity = fuelCapacity;
        this.fuelLevel = fuelLevel;

        // Set background image based on the type of car
        this.backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/car" + type + "-nobg.png"))).getImage();

        // Scale the image to make the car smaller
        int newWidth = backgroundImage.getWidth(this) * 2 / 3;
        int newHeight = backgroundImage.getHeight(this) * 2 / 3;
        this.backgroundImage = backgroundImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        // Set background color to transparent
        this.setOpaque(false);
        // Set size of the car based on the background image
        this.setSize(newWidth, newHeight);
    }

    @Override
    public void update() {
        if (destination == null) {
            return;
        }

        // The smallest possible distance the car can move in one frame
        double EPSILON = Math.max(MIN_SPEED, speed);
        // if the car is not stationary, then move at max speed towards the destination and decelerate when close
        // if the car is stationary, then accelerate towards the destination (without decelerating when close to the destination)
        double distance = destination.distance(x, y);
        if (distance < EPSILON || (speed < MIN_SPEED && !isLeaving)) {
            arriveAtDestination();
        } else {
            moveTowardsDestination(distance);
        }
        // Update the location of the car
        setLocation(x, y);
    }

    private void moveTowardsDestination(double distance) {
        double dx = destination.x - x; // Distance in x direction
        double dy = destination.y - y; // Distance in y direction
        double angle = Math.atan2(dy, dx);

        // Move the car towards the destination
        x += (int) (speed * Math.cos(angle));
        y += (int) (speed * Math.sin(angle));

        if (isLeaving) {
            // Accelerate towards the destination based on the distance between the car and the old location
            double oldDistance = oldLocation.distance(x, y);
            speed = Math.min(MAX_SPEED, exp_acceleration(oldDistance, MAX_SPEED, 128));
            speed = Math.max(speed, MIN_SPEED);
        } else {
            // Decelerate when close to the destination based on the distance between the car and the destination
            speed = Math.min(MAX_SPEED, exp_acceleration(distance, MAX_SPEED, 48));
        }
    }

    private void arriveAtDestination() {
        x = destination.x;
        y = destination.y;
        oldLocation = new Point(x, y);
        destination = null;
        speed = 0;
        isLeaving = true;
    }

    /**
     * Exponential acceleration function
     *
     * @param distance     The distance between the car and the destination
     * @param max_speed    The maximum speed of the car
     * @param decay        The decay factor, which determines how quickly the car accelerates (lower values = faster acceleration)
     * @return The speed of the car
     */
    private double exp_acceleration(double distance, double max_speed, double decay) {
        return max_speed * (1 - Math.exp(-distance / decay));
    }

    public void moveTowards(int x, int y) {
        destination = new Point(x, y);
    }

    public boolean isAtDestination() {
        return destination == null;
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        this.x = x;
        this.y = y;
    }


    public void refuel(GasPump gasPump) {
        int fuelNeeded = fuelCapacity - fuelLevel;
        int fuelDispensed = gasPump.dispenseFuel(fuelNeeded);
        fuelLevel += fuelDispensed;

        // Start the animation
        Thread thread = new Thread(gasPump);
        thread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}