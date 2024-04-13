package com.wow.yieldify;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class controller {



    @GetMapping("/historical-data/{symbol}")
    @ResponseBody

    public ResponseEntity<MetricsResponse> getHistoricalData(@PathVariable String symbol,
                                                    @RequestBody DataModel datamodel) {

        String url = buildUrl(symbol, datamodel.fromDate, datamodel.toDate);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return handleApiResponse(response, datamodel.numberOfDaysToExpiry);
        } catch (RestClientException e) {
            MetricsResponse errorResponse = new MetricsResponse(-1, -1, -1, -1, -1, -1, -1); // Initialize with default values
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    private String buildUrl(String symbol, String fromDate, String toDate) {
        return "https://api.upstox.com/v2/historical-candle/" + symbol + "/day/" + toDate + "/" + fromDate;
    }

    private ResponseEntity<MetricsResponse> handleApiResponse(ResponseEntity<String> response, int x) {
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

                double xDaysVolatility = Math.sqrt(x) * secondStdDeviation;

                double xDayMean = Math.abs(x * average);

                double upperRange = xDayMean + xDaysVolatility;

                double lowerRange = xDayMean - xDaysVolatility;

                double marketPrice = closingPrices.get(0);

                double upperPoint = (upperRange / 100) * marketPrice;

                double lowerPoint = (lowerRange / 100) * marketPrice;

                double upperBound = upperPoint + marketPrice;

                double lowerBound = marketPrice + lowerPoint;

                // Format and return the results
                // MetricsResponse metricsResponse = new MetricsResponse(average, dailyVolatility, daysVolatility, upperBound, lowerBound, marketPrice, dayMean);

                return ResponseEntity.ok(new MetricsResponse(average, dailyVolatility, xDaysVolatility, upperBound, lowerBound, marketPrice, xDayMean));
            } catch (Exception e) {
                MetricsResponse errorResponse = new MetricsResponse(-1, -1, -1, -1, -1, -1, -1); // Initialize with default values
                return ResponseEntity.internalServerError().body(errorResponse);
            } 
        } else {
            MetricsResponse errorResponse = new MetricsResponse(-1, -1, -1, -1, -1, -1, -1); // Initialize with default values
            return ResponseEntity.status(statusCode).body(errorResponse);
            
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
