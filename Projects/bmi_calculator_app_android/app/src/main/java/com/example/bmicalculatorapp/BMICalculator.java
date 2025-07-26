package com.example.bmicalculatorapp;

public class BMICalculator {
    private final int height; // in cm
    private final int weight; // in kg

    public BMICalculator(int height, int weight) {
        this.height = height;
        this.weight = weight;
    }

    public float getBMI() {
        float heightInMeters = height / 100f;
        return weight / (heightInMeters * heightInMeters);
    }

    public String getCategory() {
        float bmi = getBMI();
        if (bmi < 18.5) return "Underweight";
        else if (bmi < 25) return "Normal";
        else if (bmi < 30) return "Overweight";
        else return "Obese";
    }
}
