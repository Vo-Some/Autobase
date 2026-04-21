package com.autobase.main.order;

import lombok.Getter;

public class Cargo
{
    private String destination;
    @Getter
    private int weight;
    @Getter
    private String type;

    public Cargo(int weight)
    {
        this.destination = destination;
        this.weight = weight;
        this.type = type;
    }

}
