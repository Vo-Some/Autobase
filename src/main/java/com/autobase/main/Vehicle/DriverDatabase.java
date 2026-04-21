package com.autobase.main.Vehicle;

import java.io.*;
import java.util.*;

public class DriverDatabase {
    private static DriverDatabase instance;
    private static final String DRIVERS_FILE = "drivers.dat";
    private Map<String, Driver> drivers;

    private DriverDatabase() {
        this.drivers = new HashMap<>();
        loadDrivers();
    }

    public static synchronized DriverDatabase getInstance() {
        if (instance == null) {
            instance = new DriverDatabase();
        }
        return instance;
    }

    public void addDriver(Driver driver) {
        if (driver != null && driver.getName() != null) {
            drivers.put(driver.getName(), driver);
            saveDrivers();
        }
    }

    public Driver getDriver(String name) {
        return drivers.get(name);
    }

    public void removeDriver(String name) {
        if (drivers.remove(name) != null) {
            saveDrivers();
        }
    }

    public List<Driver> getAllDrivers() {
        return new ArrayList<>(drivers.values());
    }

    public void printAllDrivers() {
        if (drivers.isEmpty()) {
            System.out.println("No drivers in database.");
            return;
        }
        System.out.println("All Drivers");
        drivers.values().forEach(Driver::print);
    }

    private void saveDrivers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DRIVERS_FILE))) {
            oos.writeObject(new HashMap<>(drivers));
            System.out.println("Drivers saved to " + DRIVERS_FILE);
        } catch (IOException e) {
            System.err.println("Error saving drivers: " + e.getMessage());
        }
    }

    private void loadDrivers() {
        File file = new File(DRIVERS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DRIVERS_FILE))) {
                Map<String, Driver> loadedDrivers = (Map<String, Driver>) ois.readObject();
                this.drivers = loadedDrivers;
                System.out.println("Drivers loaded from " + DRIVERS_FILE);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading drivers: " + e.getMessage());
                this.drivers = new HashMap<>();
            }
        } else {
            System.out.println("No existing driver found. Adding new driver.");
        }
    }

    public int getDriverCount() {
        return drivers.size();
    }
}

