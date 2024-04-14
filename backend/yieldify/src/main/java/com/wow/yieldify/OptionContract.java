package com.wow.yieldify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionContract {
    private String name;
    private double strikePrice;
    private double upperBound;
    private double lowerBound;
}