package com.haoshen.money.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class HoldAndProfitDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer userId;
    private String investId;
    private Integer direction;
    private Float currentNum;
    private Float currentPrice;
    private Float profit;
    private String records;
    private String comment;
    private String name;
    private Float sell;
    private Float buy;
    private Float virtualProfit;
    private String updatedAt;
}
