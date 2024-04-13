package com.wow.yieldify;

public class AnalyticsResponse {
    private double average;
    private double dailyVolatility;
    private double fiveDaysVolatility;
    private double upperBound;
    private double lowerBound;
    private String errorMessage;

    // Constructors, getters, and setters

    public AnalyticsResponse() {
    }

    public AnalyticsResponse(double average, double dailyVolatility, double fiveDaysVolatility, double upperBound, double lowerBound) {
        this.average = average;
        this.dailyVolatility = dailyVolatility;
        this.fiveDaysVolatility = fiveDaysVolatility;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    public AnalyticsResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}