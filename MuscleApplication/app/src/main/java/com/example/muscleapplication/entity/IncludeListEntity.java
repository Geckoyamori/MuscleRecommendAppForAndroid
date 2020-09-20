package com.example.muscleapplication.entity;

import java.io.Serializable;
import java.util.List;

public class IncludeListEntity implements Serializable {
    private List<ResultEntity> resultEntityList;
    private int optimalSet;
    private int optimalRep;
    private double optimalWeight;
    private double optimalVolume;

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

    public List<ResultEntity> getResultEntityList() {
        return resultEntityList;
    }

    public void setResultEntityList(List<ResultEntity> resultEntityList) {
        this.resultEntityList = resultEntityList;
    }
}
