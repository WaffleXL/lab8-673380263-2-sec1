package com.example.demo.strategy;

public class DiscountContext {

    private final DiscountStrategy strategy;

    public DiscountContext(String discountType) {
        this.strategy = resolveStrategy(discountType);
    }

    private DiscountStrategy resolveStrategy(String discountType) {
        if (discountType == null) {
            return new NoDiscountStrategy();
        }

        return switch (discountType.toUpperCase()) {
            case "STUDENT", "MEMBER" -> new StudentDiscountStrategy();
            case "SEASONAL" -> new SeasonalDiscountStrategy();
            default -> new NoDiscountStrategy();
        };
    }

    public double calculatePrice(double price) {
        return strategy.apply(price);
    }

    public String getDiscountName() {
        return strategy.getName();
    }
}

