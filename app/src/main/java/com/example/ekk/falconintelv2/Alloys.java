package com.example.ekk.falconintelv2;

public class Alloys {

    private String name;
    private double meltingPoint;

    public Alloys(){}
    public Alloys(String name, double meltingPoint){
        this.name = name;
        this.meltingPoint = meltingPoint;
    }

    public String getName() {
        return name;
    }

    public void setMeltingPoint(double meltingPoint) {
        this.meltingPoint = meltingPoint;
    }

    public double getMeltingPoint() {

        return meltingPoint;
    }

    public void setName(String name) {

        this.name = name;
    }
}
