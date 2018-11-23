package com.haoshen.money.dto;

import lombok.Data;

@Data
public class AccountDto {
    private Integer userId;
    private Integer investId;
    private Integer direction;
    private Integer type;
    private Double num;
    private Double price;
}
