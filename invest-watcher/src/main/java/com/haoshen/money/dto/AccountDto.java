package com.haoshen.money.dto;

import lombok.Data;

@Data
public class AccountDto {
    private Integer userId;
    private String investId;
    private Integer direction;
    private Integer type;
    private Float num;
    private Float price;
}
