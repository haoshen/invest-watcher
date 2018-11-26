package com.haoshen.money.dto;

import lombok.Data;

// 持仓记录中的一条操作记录
@Data
public class HoldOperRecordDto {

    private Float currentNum;
    private Float currentPrice;
    private Float currentProfit;
    private String operTime;
    private Integer operType;
    private Float operNum;
    private Float operPrice;
    private Float operProfit;
}
