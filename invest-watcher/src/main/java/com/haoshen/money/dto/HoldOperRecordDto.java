package com.haoshen.money.dto;

import lombok.Data;

// 持仓记录中的一条操作记录
@Data
public class HoldOperRecordDto {

    private Double currentNum;
    private Double currentPrice;
    private Double currentProfit;
    private String operTime;
    private Integer operType;
    private Double operNum;
    private Double operPrice;
    private Double operProfit;
}
