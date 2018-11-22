package com.haoshen.money.dto;

import lombok.Data;

@Data
public class HoldOperRecord {

    private double currentNum;
    private double currentPrice;
    private double currentProfit;
    private String operTime;
    private int    operType;
    private double operNum;
    private double operPrice;
    private double operProfit;
}
