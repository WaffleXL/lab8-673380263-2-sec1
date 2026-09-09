package com.example.demo.strategy;

public class StudentDiscountStrategy implements DiscountStrategy {

    @Override
    public double apply(double price) {
        return price * 0.90;
    }

    @Override
    public String getName() {
        return "ส่วนลดนักศึกษา (10%)";
    }
}

