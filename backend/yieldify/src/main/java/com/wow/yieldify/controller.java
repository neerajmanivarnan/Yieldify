package com.wow.yieldify;

import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Document;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import kong.unirest.Unirest;
import kong.unirest.UnirestException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class controller {

    @GetMapping("/historical-data/{symbol}/{fromDate}/{toDate}")
    public ResponseEntity<String> getHistoricalData(@PathVariable String symbol,
                                                    @PathVariable String fromDate,
                                                    @PathVariable String toDate) {

        String url = buildUrl(symbol, fromDate, toDate);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return handleApiResponse(response);
        } catch (RestClientException e) {
            return ResponseEntity.internalServerError().body("Error fetching data: " + e.getMessage());
        }
    }

    private String buildUrl(String symbol, String fromDate, String toDate) {
        return "https://api.upstox.com/v2/historical-candle/" + symbol + "/day/" + toDate + "/" + fromDate;
    }

    private ResponseEntity<String> handleApiResponse(ResponseEntity<String> response) {
        int statusCode = response.getStatusCodeValue(); 
        String responseBody = response.getBody();
    
        if (statusCode == 200) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                JsonNode candlesNode = jsonNode.path("data").path("candles");

                List<Double> closingPrices = new ArrayList<>();
                for (JsonNode candle : candlesNode) {
                    closingPrices.add(candle.get(4).asDouble());
                }

                List<Double> dailyReturns = calculateDailyReturns(closingPrices);

                double average = calculateAverage(dailyReturns);

                double dailyVolatility = calculateVolatility(dailyReturns);

                double secondStdDeviation = 2 * dailyVolatility;

                double fiveDaysVolatility = Math.sqrt(5) * secondStdDeviation;

                double fiveDayMean = 5 * average;

                double upperRange = fiveDayMean + fiveDaysVolatility;

                double lowerRange = fiveDayMean - fiveDaysVolatility;

                double marketPrice = closingPrices.get(0);

                double upperPoint = (upperRange / 100) * marketPrice;

                double lowerPoint = (lowerRange / 100) * marketPrice;

                double upperBound = upperPoint + marketPrice;

                double lowerBound = marketPrice - lowerPoint;

                // Format and return the results
                String result = String.format("Average: %.2f, Daily Volatility: %.2f, 5 Days Volatility: %.2f, Upper Bound: %.2f, Lower Bound: %.2f, marketPrice: %.2f",
                        average, dailyVolatility, fiveDaysVolatility, upperBound, lowerBound, marketPrice);

                return ResponseEntity.ok(result);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body("Error parsing JSON: " + e.getMessage());
            } 
        } else {
                return ResponseEntity.status(statusCode).body("Error: " + responseBody);
            
        }   
    }

    private List<Double> calculateDailyReturns(List<Double> closingPrices) {
        List<Double> dailyReturns = new ArrayList<>();
        for (int i = 1; i < closingPrices.size(); i++) {
            double previousClosingPrice = closingPrices.get(i - 1);
            double currentClosingPrice = closingPrices.get(i);
            double dailyReturn = (Math.log(currentClosingPrice / previousClosingPrice)) * 100;
            dailyReturns.add(dailyReturn);
        }
        return dailyReturns;
    }

    private double calculateAverage(List<Double> values) {
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.size();
    }

    private double calculateVolatility(List<Double> values) {
        double average = calculateAverage(values);
        double sumOfSquares = 0;
        for (double value : values) {
            sumOfSquares += Math.pow(value - average, 2);
        }
        double variance = sumOfSquares / values.size();
        return Math.sqrt(variance);
    }
}
