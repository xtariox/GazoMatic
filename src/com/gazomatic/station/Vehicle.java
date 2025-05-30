package com.gazomatic.station;

import com.gazomatic.IAnimatable;
import com.gazomatic.Time;

import javax.swing.*;
import java.awt.*;

public class Vehicle extends JPanel implements IAnimatable {
    int fuelLevel;
    Image backgroundImage;

    private static final double MAX_SPEED = 500;
    private double speed = MAX_SPEED;
    private static final double MIN_SPEED = 10;
    private double x = 0;
    private double y = 0;
    private Point destination = null;
    private Point oldLocation = new Point((int)x, (int)y);
    private boolean isLeaving = false; // Flag to track if car is leaving (will be set to true when after the first stop)


    @Override
    public void update() {
        if (destination == null) {
            return;
        }

        // The smallest possible distance the car can move in one frame
        double EPSILON = Math.max(MIN_SPEED, speed) * Time.getDeltaTime();
        // if the car is not stationary, then move at max speed towards the destination and decelerate when close
        // if the car is stationary, then accelerate towards the destination (without decelerating when close to the destination)
        double distance = destination.distance(x, y);
        if (distance < EPSILON || (speed < MIN_SPEED && !isLeaving)) {
            arriveAtDestination();
        } else {
            moveTowardsDestination(distance);
        }
        // Update the location of the car
        setLocation((int)x, (int)y);
    }

    private void moveTowardsDestination(double distance) {
        double dx = destination.x - x; // Distance in x direction
        double dy = destination.y - y; // Distance in y direction
        double angle = Math.atan2(dy, dx);

        double DELTA_TIME = Time.getDeltaTime();

        // Move the car towards the destination
        x += speed * Math.cos(angle) * DELTA_TIME;
        y += speed * Math.sin(angle) * DELTA_TIME;

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
        oldLocation = new Point((int)x, (int)y);
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
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
