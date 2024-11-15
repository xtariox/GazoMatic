package com.gazomatic.station;

import com.gazomatic.IAnimated;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Truck extends Vehicule {

    private final GasPump gasPump;

    public Truck(GasPump gasPump) {
        this.gasPump = gasPump;
        this.backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/truck-nobg.png"))).getImage();
        this.setOpaque(false);
        // Scale the image to make the truck smaller
        int newWidth = backgroundImage.getWidth(this) * 5 / 4;
        int newHeight = backgroundImage.getHeight(this) * 5 / 4;
        this.backgroundImage = backgroundImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        this.setSize(newWidth, newHeight);

    }

    public void refillStation(GasPump gasPump) {
        System.out.println("Truck is refilling the pump.");
        gasPump.refill();
        // Start gas pump animation in a new thread
        Thread thread = new Thread(gasPump);
        thread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

}
