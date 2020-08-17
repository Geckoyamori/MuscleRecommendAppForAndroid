package com.example.muscleapplication.entity;

import java.io.Serializable;

public class ResultEntity implements Serializable {
    private int optimalSet;
    private int optimalRep;
    private double optimalWeight;
    private double optimalVolume;
    private double weight1;
    private double rep1;



    public double getRep1() {
        return rep1;
    }

    public void setRep1(double rep1) {
        this.rep1 = rep1;
    }

    public double getWeight1() {

        return weight1;
    }

    public void setWeight1(double weight1) {
        this.weight1 = weight1;
    }

    public double getOptimalVolume() {
        return optimalVolume;
    }

    public void setOptimalVolume(double optimalVolume) {
        this.optimalVolume = optimalVolume;
    }

    public double getOptimalWeight() {
        return optimalWeight;
    }

    public void setOptimalWeight(double optimalWeight) {
        this.optimalWeight = optimalWeight;
    }

    public int getOptimalRep() {
        return optimalRep;
    }

    public void setOptimalRep(int optimalRep) {
        this.optimalRep = optimalRep;
    }

    public int getOptimalSet() {
        return optimalSet;
    }

    public void setOptimalSet(int optimalSet) {
        this.optimalSet = optimalSet;
    }
}
