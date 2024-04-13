package com.wow.yieldify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor                                                       
public class DataModel {
    String fromDate;
    String toDate;
    int numberOfDaysToExpiry;
}
