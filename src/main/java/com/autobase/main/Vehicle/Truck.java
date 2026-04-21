package com.autobase.main.Vehicle;

import java.io.*;
import java.util.Scanner;

public class Truck extends Vehicle {

    public Truck(String m, float c) {
        super(m, c);
    }

    public Truck() {
        super();
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
        System.out.print("Model: ");
        this.model = sc.nextLine();
        System.out.print("Capacity: ");
        this.capacity = sc.nextFloat();
        sc.nextLine();
    }

    @Override
    public String toString() {
        return "Truck{" +
                "model='" + model + '\'' +
                ", capacity=" + capacity +
                ", driver=" + driver +
                '}';
    }
}
