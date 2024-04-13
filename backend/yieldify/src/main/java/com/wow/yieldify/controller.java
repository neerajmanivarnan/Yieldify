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

// @RestController
// public class controller {
    

//     // @GetMapping("historical-data")
//     // public void getHistoricalData(){

//     // }


//     @GetMapping("/nifty-fifty/{startDate}/{endDate}")
// public ResponseEntity<List<NiftyData>> getNiftyFiftyData(@PathVariable String startDate, 
//                                                          @PathVariable String endDate, 
//                                                          RestTemplate restTemplate) {
//     LocalDate start = LocalDate.parse(startDate);
//     LocalDate end = LocalDate.parse(endDate);
//     List<NiftyData> dailyData = new ArrayList<>();

//     for (LocalDate date = start; date.isBefore(end.plusDays(1)); date = date.plusDays(1)) {
//         String formattedDate = date.toString();
//         // Call the API with formattedDate and parse the response to NiftyData object
//         NiftyData dailyInfo = parseNiftyDataFromHtml(restTemplate.getForEntity(buildUrl(formattedDate), String.class).getBody());
//         dailyData.add(dailyInfo);
//     }

//     return ResponseEntity.ok(dailyData);
// }

// private String buildUrl(String date) {
//     final String baseUrl = "https://www1.nseindia.com/products/content/equities/equities/eq_security.htm";
//     final String symbol = "NIFTY";

//     StringBuilder urlBuilder = new StringBuilder(baseUrl);
//     urlBuilder.append("?symbol=").append(symbol);
//     urlBuilder.append("&fromDate=").append(date);
//     urlBuilder.append("&toDate=").append(date);

//     // You can add other parameters like expiryDate, segmentLink, dataType, etc.
//     // Refer to NSE documentation for details

//     return urlBuilder.toString();
// }


// private NiftyData parseNiftyDataFromHtml(String htmlData) {
//     Document doc = (Document) Jsoup.parse(htmlData);

//     // Identify the elements containing the data points (prices, volumes, etc.)
//     // This might involve selecting specific tables, rows, or classes based on NSE's HTML structure
//     // You'll need to inspect the actual HTML response to determine the selectors

//     String openPrice = ((Element) doc).select("selector_for_open_price").text();
//     String closePrice = ((Element) doc).select("selector_for_close_price").text();
//     String volume = ((Element) doc).select("selector_for_volume").text();

//     // Extract other data points you're interested in following the same logic

//     NiftyData dailyData = new NiftyData(openPrice, closePrice, volume);
//     // Set other data points in the NiftyData object

//     return dailyData;
// }


// public class NiftyData {

//     private String date;
//     private double openPrice;
//     private double closePrice;
//     private long volume;

//     // Constructor with all fields
//     public NiftyData(String date, double openPrice, double closePrice, long volume) {
//         this.date = date;
//         this.openPrice = openPrice;
//         this.closePrice = closePrice;
//         this.volume = volume;
//     }

//     public NiftyData(String openPrice2, String closePrice2, String volume2) {
//         //TODO Auto-generated constructor stub
//     }

//     // Getters for each field
//     public String getDate() {
//         return date;
//     }

//     public double getOpenPrice() {
//         return openPrice;
//     }

//     public double getClosePrice() {
//         return closePrice;
//     }

//     public long getVolume() {
//         return volume;
//     }
// }




// }


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
