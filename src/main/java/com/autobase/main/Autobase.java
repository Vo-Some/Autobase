package com.autobase.main;

import com.autobase.main.Vehicle.Driver;
import com.autobase.main.Vehicle.DriverDatabase;
import com.autobase.main.Vehicle.Truck;
import com.autobase.main.Vehicle.Vehicle;
import com.autobase.main.order.typeoforder.CargoType;
import com.autobase.main.order.typeoforder.ExpressOrder;
import com.autobase.main.order.Order;
import com.autobase.main.order.typeoforder.RegularOrder;
import com.autobase.main.order.OrderRepository;

import java.io.*;
import java.util.*;

public class Autobase {
    private List<Order> orders = new ArrayList<>();

    public Autobase() {}

    public void addOrMergeOrder(Order newOrder) {
        for (Order existing : orders) {
            if (existing.canMergeWith(newOrder)) {
                existing.mergeWeight(newOrder.getWeight());
                System.out.println("Order merged.");
                return;
            }
        }
        orders.add(newOrder);
        System.out.println("Order added.");
    }

    public void printAll() {
        if (orders.isEmpty()) {
            System.out.println("No orders.");
            return;
        }
        for (Order o : orders) {
            o.print();
        }
    }

    //
    public void saveToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Order o : orders) {
                StringBuilder sb = new StringBuilder();
                sb.append(o instanceof ExpressOrder ? "EXPRESS" : "REGULAR").append(";");
                sb.append(o.getFrom()).append(";").append(o.getTo()).append(";")
                  .append(o.getCargoType()).append(";").append(o.getWeight()).append(";");
                if (o instanceof ExpressOrder eo) {
                    sb.append(eo.getExtraCharge()).append(";");
                } else {
                    sb.append("0;");
                }
                Vehicle v = o.getVehicle();
                if (v instanceof Truck t) {
                    sb.append(t.getModel()).append(";")
                      .append(t.getCapacity()).append(";");
                    Driver d = t.getDriver();
                    if (d != null) {
                        sb.append(d.getName()).append(";").append(d.getExperience());
                    } else {
                        sb.append("unknown;0");
                    }
                } else {
                    sb.append("none;0;none;0");
                }
                pw.println(sb);
            }
            System.out.println("Saved to " + filename);
        } catch (IOException e) {
            System.out.println("Save error: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] f = line.split(";");
                // f[0]=type, f[1]=from, f[2]=to, f[3]=cargoType, f[4]=cargoWeight,
                // f[5]=extraCharge, f[6]=truckModel, f[7]=capacity, f[8]=driverName, f[9]=driverExp
                Order order;
                if ("EXPRESS".equals(f[0])) {
                    order = new ExpressOrder(f[1], f[2], CargoType.valueOf(f[3]),
                            Float.parseFloat(f[4]), Float.parseFloat(f[5]));
                } else {
                    order = new RegularOrder(f[1], f[2], CargoType.valueOf(f[3]),
                            Float.parseFloat(f[4]));
                }
                if (f.length > 6 && !"none".equals(f[6])) {
                    Driver driver = new Driver(f[8], Integer.parseInt(f[9]));
                    Truck truck = new Truck(f[6], Float.parseFloat(f[7]));
                    truck.setDriver(driver);
                    order.setVehicle(truck);
                }
                orders.add(order);
            }
            System.out.println("Loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Load error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Autobase().showMenu();
    }

    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        // Load orders at startup
        this.orders = OrderRepository.loadOrders();

        int choice;
        do {
            System.out.println("\n Autobase Menu");
            System.out.println("1. Add Regular Order");
            System.out.println("2. Add Express Order");
            System.out.println("3. Print All Orders");
            System.out.println("4. Save orders");
            System.out.println("5. Load orders");
            System.out.println("6. Load orders.txt");
            System.out.println("7. Manage Drivers");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> {
                    RegularOrder ro = new RegularOrder();
                    ro.input();
                    addOrMergeOrder(ro);
                }
                case 2 -> {
                    ExpressOrder eo = new ExpressOrder();
                    eo.input();
                    addOrMergeOrder(eo);
                }
                case 3 -> printAll();
                case 4 -> {
                    OrderRepository.saveOrders(orders);
                }
                case 5 -> {
                    this.orders = OrderRepository.loadOrders();
                }
                case 6 -> {
                    System.out.print("Enter txt file path: ");
                    String path = sc.nextLine();
                    this.orders = OrderRepository.loadFromTxt(path);
                }
                case 7 -> showDriverMenu();
            }
        } while (choice != 0);

        // Save orders before exit
        OrderRepository.saveOrders(orders);
    }

    private void showDriverMenu() {
        DriverDatabase db = DriverDatabase.getInstance();
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n Driver Management");
            System.out.println("1. Add new driver");
            System.out.println("2. View all drivers");
            System.out.println("3. Remove driver");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.print(" Driver name: ");
                    String name = sc.nextLine();
                    if (db.getDriver(name) != null) {
                        System.out.println(" Driver already exists!");
                    } else {
                        System.out.print("Experience (years): ");
                        int exp = sc.nextInt();
                        sc.nextLine();
                        db.addDriver(new Driver(name, exp));
                        System.out.println(" Driver added!");
                    }
                }
                case 2 -> db.printAllDrivers();
                case 3 -> {
                    System.out.print(" Driver name to remove: ");
                    String name = sc.nextLine();
                    db.removeDriver(name);
                    System.out.println(" Driver removed!");
                }
            }
        } while (choice != 0);
    }
}
