package com.autobase.main.order;

import com.autobase.main.Vehicle.Driver;
import com.autobase.main.Vehicle.DriverDatabase;
import com.autobase.main.Vehicle.Truck;
import com.autobase.main.order.typeoforder.CargoType;
import com.autobase.main.order.typeoforder.ExpressOrder;
import com.autobase.main.order.typeoforder.RegularOrder;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class OrderRepository {
    private static final String ORDERS_FILE = "orders.dat";

    public static void saveOrders(List<Order> orders) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ORDERS_FILE))) {
            oos.writeObject(new ArrayList<>(orders));
            System.out.println("Orders saved to " + ORDERS_FILE);
        } catch (IOException e) {
            System.err.println("Error saving orders: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Order> loadOrders() {
        File file = new File(ORDERS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ORDERS_FILE))) {
                List<Order> loadedOrders = (List<Order>) ois.readObject();
                System.out.println("Orders loaded from " + ORDERS_FILE);
                return loadedOrders;
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading orders: " + e.getMessage());
                return new ArrayList<>();
            }
        } else {
            System.out.println("No existing order database found.");
            return new ArrayList<>();
        }
    }


    public static List<Order> loadFromTxt(String filePath) {
        List<Order> orders = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(";");
                // parts: [type, from, to, cargoType, weight, extraCharge/0, truckModel, capacity, driverName, experience]
                String type = parts[0].toUpperCase();
                String from = parts[1];
                String to = parts[2];
                CargoType cargoType = CargoType.valueOf(parts[3].toUpperCase());
                float weight = Float.parseFloat(parts[4]);
                float extraCharge = Float.parseFloat(parts[5]);
                String truckModel = parts[6];
                float capacity = Float.parseFloat(parts[7]);
                String driverName = parts[8];
                int experience = Integer.parseInt(parts[9]);

                Driver driver = DriverDatabase.getInstance().getDriver(driverName);
                if (driver == null) {
                    driver = new Driver(driverName, experience);
                    DriverDatabase.getInstance().addDriver(driver);
                }
                Truck truck = new Truck(truckModel, capacity);
                truck.setDriver(driver);

                Order order;
                if (type.equals("EXPRESS")) {
                    order = new ExpressOrder(from, to, cargoType, weight, extraCharge);
                } else {
                    order = new RegularOrder(from, to, cargoType, weight);
                }
                order.setVehicle(truck);
                orders.add(order);
            }
            System.out.println("Loaded " + orders.size() + " orders from " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading txt file: " + e.getMessage());
        }
        return orders;
    }

    public static void deleteOrdersFile() {
        File file = new File(ORDERS_FILE);
        if (file.exists() && file.delete()) {
            System.out.println("Orders file deleted.");
        }
    }
}

