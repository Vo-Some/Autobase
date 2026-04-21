package com.autobase.main.order;

import com.autobase.main.order.typeoforder.CargoType;
import com.autobase.main.Vehicle.Vehicle;

import java.io.*;
import java.util.List;

public abstract class Order implements Externalizable {
    private static final long serialVersionUID = 1L;

    protected String from;
    protected String to;
    protected CargoType cargoType;
    protected float cargoWeight;
    protected Vehicle vehicle;

    public static float GLOBAL_RATE = 1.0f;

    public Order() {}

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(from != null ? from : "");
        out.writeUTF(to != null ? to : "");
        out.writeObject(cargoType);
        out.writeFloat(cargoWeight);
        out.writeObject(vehicle);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.from = in.readUTF();
        this.to = in.readUTF();
        this.cargoType = (CargoType) in.readObject();
        this.cargoWeight = in.readFloat();
        this.vehicle = (Vehicle) in.readObject();
    }

    public abstract void input();

    public abstract void print();

    @Override
    public abstract String toString();

    public boolean canMergeWith(Order other) {
        return this.cargoType == other.cargoType && this.to.equals(other.to);
    }

    public void mergeWeight(float w) {
        this.cargoWeight += w;
    }

    public int loadFields(List<String> fields, int startIndex) {
        int i = startIndex;
        this.from = fields.get(i++);
        this.to = fields.get(i++);
        this.cargoType = CargoType.valueOf(fields.get(i++));
        this.cargoWeight = Float.parseFloat(fields.get(i++));
        return i;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public CargoType getCargoType() {
        return cargoType;
    }

    public float getWeight() {
        return cargoWeight;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle v) {
        this.vehicle = v;
    }
}
