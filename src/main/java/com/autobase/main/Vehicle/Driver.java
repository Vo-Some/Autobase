package com.autobase.main.Vehicle;

import java.io.Serializable;

public class Driver implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private int experience;

    public Driver(String n, int exp) {
        this.name = n;
        this.experience = exp;
    }

    public Driver() {
        this("", 0);
    }


    public void addExperience(int years) {
        this.experience += years;
    }

    public void print() {
        System.out.println("Driver: " + name + ", experience: " + experience + " years");
    }

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + name + '\'' +
                ", experience=" + experience +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }

}
