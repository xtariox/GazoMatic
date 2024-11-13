package com.gazomatic.station;

import com.gazomatic.IAnimated;

import javax.swing.*;

public class GasPump extends JProgressBar implements IAnimated {
    private int currentFuelLevel;
    private int maxFuelLevel;

    public GasPump(int maxFuelLevel) {
        super(JProgressBar.VERTICAL, 0, maxFuelLevel);
        this.maxFuelLevel = maxFuelLevel;
        this.currentFuelLevel = maxFuelLevel;
        setValue(currentFuelLevel);
    }

    public int dispenseFuel(int amount) {
        if (amount > 0 && currentFuelLevel - amount < 0) {
            return 0;
        }
        System.out.println("Dispensing " + amount + " liters of fuel. Current fuel level: " + currentFuelLevel);
        currentFuelLevel -= amount;
        return amount;
    }

    public void refill(){
        System.out.println("Refilling fuel tank");
        currentFuelLevel = maxFuelLevel;
        updateFuelLevel();
    }

    public void updateFuelLevel() {
        setValue(currentFuelLevel);
    }

    public int getCurrentFuelLevel() {
        return currentFuelLevel;
    }

    public boolean isDoneUpdating() {
        return currentFuelLevel == getValue();
    }

    @Override
    public void update() {
        // Animate the fuel level change
        if (currentFuelLevel < getValue()) {
            setValue(getValue() - 1);
        } else if (currentFuelLevel > getValue()) {
            setValue(getValue() + 1);
        }
    }
}

