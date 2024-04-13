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
        // Replace with the actual Upstox API URL structure
        return "https://api.upstox.com/v2/historical-candle/" + symbol + "/day/" + toDate + "/" + fromDate;
    }

    private ResponseEntity<String> handleApiResponse(ResponseEntity<String> response) {
        int statusCode = response.getStatusCodeValue(); // Use getStatusCodeValue()
        String responseBody = response.getBody();
    
        if (statusCode == 200) {
            // You might need to parse the JSON response body based on Upstox API format
            // Consider using a library like jackson-databind for easier parsing
    
            return ResponseEntity.ok(responseBody); // Return the raw JSON response
        } else {
            return ResponseEntity.status(statusCode).body("Error: " + responseBody);
        }
    }
    
}
