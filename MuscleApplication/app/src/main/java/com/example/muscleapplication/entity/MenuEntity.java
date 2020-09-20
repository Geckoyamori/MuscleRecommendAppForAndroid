package com.example.muscleapplication.entity;

import java.io.Serializable;

public class MenuEntity implements Serializable {
    private String trainingName;
    private String trainingCategory;
    private String trainingPart;
    private double maxWeight;
    private int setCount;

    public int getSetCount() {
        return setCount;
    }

    public void setSetCount(int setCount) {
        this.setCount = setCount;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public String getTrainingCategory() {
        return trainingCategory;
    }

    public void setTrainingCategory(String trainingCategory) {
        this.trainingCategory = trainingCategory;
    }

    public String getTrainingPart() {
        return trainingPart;
    }

    public void setTrainingPart(String trainingPart) {
        this.trainingPart = trainingPart;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }
}
