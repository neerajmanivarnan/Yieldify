package com.wow.yieldify;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MetricsResponse {

    @JsonProperty("average")
    private double average;

    @JsonProperty("daily_volatility")
    private double dailyVolatility;

    @JsonProperty("x_days_volatility")
    private double xDaysVolatility;

    @JsonProperty("upper_bound")
    private double upperBound;

    @JsonProperty("lower_bound")
    private double lowerBound;

    @JsonProperty("market_price")
    private double marketPrice;

    @JsonProperty("x_day_mean")
    private double xDayMean;

    public MetricsResponse(double average, double dailyVolatility, double xDaysVolatility, double upperBound, double lowerBound, double marketPrice, double xDayMean) {
        this.average = average;
        this.dailyVolatility = dailyVolatility;
        this.xDaysVolatility = xDaysVolatility;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
        this.marketPrice = marketPrice;
        this.xDayMean = xDayMean;
    }
}
