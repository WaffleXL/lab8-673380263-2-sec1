package com.example.demo.strategy;

public class SeasonalDiscountStrategy implements DiscountStrategy {

    @Override
    public double apply(double price) {
        return price * 0.80;
    }

    @Override
    public String getName() {
        return "ส่วนลดเทศกาล (20%)";
    }
}
