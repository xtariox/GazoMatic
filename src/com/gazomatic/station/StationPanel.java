package com.gazomatic.station;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Random;
import com.gazomatic.IAnimated;
import com.gazomatic.Time;

public class StationPanel extends JPanel implements IAnimated {
    private Image backgroundImage;
    private GasPump gasPump;
    private Car car;
    private Truck truck;
    private int PUMP_X;
    private int PUMP_Y;
    private int CAR_Y;
    private int TRUCK_Y;
    private boolean isLeavingStation = false; // Flag to track if car is leaving
    private boolean TruckisLeavingStation = false; // Flag to track if truck is leaving
    private boolean isRefueling = false; // Flag to track if car is refueling
    private boolean isTruckRefilling = false; // Flag to track if truck is refilling
    private int LAST_CAR = -1; // The last car type

    public StationPanel() {
        backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/bg.png"))).getImage();
        gasPump = new GasPump(500);

        this.setLayout(null);
        this.add(gasPump);
        this.setComponentZOrder(gasPump, 0);
    }

    public void init(){
        int barWidth = 24;
        int barHeight = 128;
        int xOffset = 76;

        PUMP_X = getWidth() / 2;
        PUMP_Y = getHeight() / 3 + 128;
        CAR_Y = PUMP_Y - 72;
        TRUCK_Y = PUMP_Y - 370;

        int xc = PUMP_X - xOffset - barWidth / 2;
        int yc = PUMP_Y - barHeight / 2;

        gasPump.setBounds(xc, yc, barWidth, barHeight); //

        // Start the thread to update the station panel
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void update() {
        if (car == null) {
            handleNoCar();
        } else if (car.isAtDestination()) {
            handleCarAtDestination();
        }
        if (truck != null && truck.isAtDestination()) {
            TruckAtDestination();
        }

//        // Refuel the car if it's at the pump
//        refuelCar();
//
//        // Check if car should leave the station after refueling
//        if (car.getFuelLevel() == car.getFuelCapacity() && !isLeavingStation) {
//            leaveStation();
//        }
//
//        // Remove the car only when it has moved fully off-screen to the left
//        if (isLeavingStation && car.getX() < -car.getWidth()) {
//            this.remove(car);  // Remove the car from the panel
//            car = null;         // Allow a new car to be created next update
//            isLeavingStation = false;  // Reset the flag for the next car
//        }
    }

    private void handleNoCar() {
        if (gasPump.getCurrentFuelLevel() < 50 && truck == null) {
            System.out.println("Gas pump is empty. Refilling...");
            //! Do the truck thing here
            truck = createTruck();

            return;
        }
        if (truck == null && car == null) {
            car = createNewCar();
        }

    }

    private Car createNewCar() {
        // Create a new car of a random type if there is no car
        Random random = new Random(Time.getTime());
        int type;
        do {
            type = random.nextInt(4) + 1;
        } while (type == LAST_CAR);
        LAST_CAR = type;

        // Give the car a random fuel level between 0 and 50 (or less if the pump is almost empty to make sure the pump has enough fuel)
        int cap = Math.max(100 - gasPump.getCurrentFuelLevel(), 0);
        int fuelLevel = Math.max(random.nextInt(50), cap);
        Car car = new Car(type, 100, fuelLevel);
        car.setLocation(getWidth(), CAR_Y);

        this.add(car);
        this.setComponentZOrder(car, 1);

        // Set the destination of the car to the pump
        car.moveTowards(PUMP_X - car.getWidth() / 2, CAR_Y);

        Thread thread = new Thread(car);
        thread.start(); // Start the thread to move the car

        return car;
    }

    private void handleCarAtDestination() {
        if (isLeavingStation) {
            removeCar();
        } else {
            if (!isRefueling) {
                refuelCar();
            }
            if (isRefueling && gasPump.isDoneUpdating()) {
                isRefueling = false;
                leaveStation();
            }
        }
    }

    private void removeCar() {
        this.remove(car);
        car = null;
        isLeavingStation = false;
    }

    public void refuelCar() {
        if (car != null) {
            System.out.println("Car is at the pump and refueling.");
            isRefueling = true;
            car.refuel(gasPump);
        }
    }

    public void leaveStation() {
        if (car != null) {
            System.out.println("Car is full and leaving the station.");
            car.moveTowards(-car.getWidth(), CAR_Y); // Move the car to the left
            isLeavingStation = true;
        }
    }

    public Truck createTruck() {
        truck = new Truck(gasPump);
        truck.setLocation(getWidth(), TRUCK_Y);
        this.add(truck);
        this.setComponentZOrder(truck, 1);
        truck.moveTowards(PUMP_X - truck.getWidth() / 2, TRUCK_Y);

        Thread thread = new Thread(truck);
        thread.start();
        return truck;
    }

    public void TruckAtDestination() {
        if (TruckisLeavingStation) {
            removeTruck();
        } else {
            if (!isTruckRefilling) {
                truck.refillStation(gasPump);
                isTruckRefilling = true;
            }
            if (isTruckRefilling && gasPump.isDoneUpdating()) {
                isTruckRefilling = false;
                TruckLeaving();
            }
        }
    }

    public void TruckLeaving() {
        if (truck != null) {
            System.out.println("Truck is done and leaving the station.");
            truck.moveTowards(-truck.getWidth(), TRUCK_Y);
            TruckisLeavingStation = true;
        }
    }

    public void removeTruck() {
        this.remove(truck);
        truck = null;
        TruckisLeavingStation = false;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}