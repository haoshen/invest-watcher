package com.haoshen.money.dto;

import java.util.Date;

import lombok.Data;

@Data
public class RealTimeMarketDto {

    private String code;
    private String name;
    private Float sell;
    private Float buy;
    private Date updatedTime;

}
