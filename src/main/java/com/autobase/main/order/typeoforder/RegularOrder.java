package com.autobase.main.order.typeoforder;

import java.io.*;
import java.util.Scanner;

import com.autobase.main.Vehicle.Driver;
import com.autobase.main.Vehicle.DriverDatabase;
import com.autobase.main.Vehicle.Truck;
import com.autobase.main.order.Order;

public class RegularOrder extends Order {

    public RegularOrder() {
        super();
    }

    public RegularOrder(String from, String to, CargoType cargoType, float cargoWeight) {
        super();
        this.from = from;
        this.to = to;
        this.cargoType = cargoType;
        this.cargoWeight = cargoWeight;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
    }

    @Override
    public void input() {
        Scanner sc = new Scanner(System.in);
        System.out.print("From: ");
        this.from = sc.nextLine();
        System.out.print("To: ");
        this.to = sc.nextLine();
        this.cargoType = CargoTypeSelector.selectCargoType(sc);
        System.out.print("Cargo weight: ");
        this.cargoWeight = sc.nextFloat();
        sc.nextLine();

        System.out.print("Driver name: ");
        String dName = sc.nextLine();
        Driver driver = DriverDatabase.getInstance().getDriver(dName);
        if (driver == null) {
            System.out.print("Driver not found. Add experience years: ");
            int dExp = sc.nextInt();
            sc.nextLine();
            driver = new Driver(dName, dExp);
            DriverDatabase.getInstance().addDriver(driver);
        }

        // Truck
        System.out.print("Truck model: ");
        String tModel = sc.nextLine();
        System.out.print("Truck capacity: ");
        float tCap = sc.nextFloat();
        sc.nextLine();
        Truck truck = new Truck(tModel, tCap);
        truck.setDriver(driver);

        this.vehicle = truck;
    }

    @Override
    public String toString() {
        float totalCost = cargoWeight * GLOBAL_RATE;
        return "RegularOrder{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", cargoType=" + cargoType +
                ", cargoWeight=" + cargoWeight +
                ", totalCost=" + totalCost +
                ", vehicle=" + vehicle +
                '}';
    }

    @Override
    public void print() {
        System.out.println(this.toString());
    }

}
