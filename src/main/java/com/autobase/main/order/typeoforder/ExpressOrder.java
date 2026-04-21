package com.autobase.main.order.typeoforder;

import java.io.*;
import java.util.Scanner;

import com.autobase.main.order.Order;
import com.autobase.main.Vehicle.Driver;
import com.autobase.main.Vehicle.DriverDatabase;
import com.autobase.main.Vehicle.Truck;

public class ExpressOrder extends Order {
    private float extraCharge;

    public ExpressOrder() {
        super();
        this.extraCharge = 0;
    }

    public ExpressOrder(String from, String to, CargoType cargoType, float cargoWeight, float extraCharge) {
        super();
        this.from = from;
        this.to = to;
        this.cargoType = cargoType;
        this.cargoWeight = cargoWeight;
        this.extraCharge = extraCharge;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeFloat(extraCharge);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        this.extraCharge = in.readFloat();
    }

    public float getExtraCharge() {
        return extraCharge;
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
        System.out.print("Extra charge: ");
        this.extraCharge = sc.nextFloat();
        sc.nextLine();

        // Driver selection from database
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
        float totalCost = (cargoWeight * GLOBAL_RATE) + extraCharge;
        return "ExpressOrder{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", cargoType=" + cargoType +
                ", cargoWeight=" + cargoWeight +
                ", extraCharge=" + extraCharge +
                ", totalCost=" + totalCost +
                ", vehicle=" + vehicle +
                '}';
    }

    @Override
    public void print() {
        System.out.println(this.toString());
    }
}
