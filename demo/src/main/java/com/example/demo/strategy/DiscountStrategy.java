package com.example.demo.strategy;

public interface DiscountStrategy {

    double apply(double price);

    String getName();
}

