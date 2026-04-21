package com.autobase.main.Vehicle;

import java.io.*;

public abstract class Vehicle implements Externalizable {
    private static final long serialVersionUID = 1L;

    protected String model;
    protected float capacity;
    protected Driver driver;
    private static int vehicleCount = 0;

    public Vehicle(String m, float c) {
        this.model = m;
        this.capacity = c;
        vehicleCount++;
    }

    public Vehicle() {
        this("", 0);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(model != null ? model : "");
        out.writeFloat(capacity);
        out.writeObject(driver);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.model = in.readUTF();
        this.capacity = in.readFloat();
        this.driver = (Driver) in.readObject();
    }

    public abstract void input();


    @Override
    public String toString() {
        return "Vehicle{" +
                "model='" + model + '\'' +
                ", capacity=" + capacity +
                ", driver=" + driver +
                '}';
    }

    public String getModel() {
        return model;
    }

    public float getCapacity() {
        return capacity;
    }

    public void setDriver(Driver d) {
        this.driver = d;
    }

    public Driver getDriver() {
        return driver;
    }

    public static int getVehicleCount() {
        return vehicleCount;
    }
}
