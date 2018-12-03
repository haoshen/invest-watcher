package com.haoshen.money.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class HoldDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer userId;
    private String investId;
    private Integer direction;
    private Float currentNum;
    private Float currentPrice;
    private Float profit;
    private String comment;
    private Integer status;
}

