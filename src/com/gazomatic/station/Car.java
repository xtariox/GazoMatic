package com.gazomatic.station;

import com.gazomatic.IAnimated;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Car extends Vehicule {
    private final int fuelCapacity;


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