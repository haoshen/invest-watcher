package com.haoshen.money.dto;

import lombok.Data;

@Data
public class Account {
    private Integer investId = null;
    private Integer direction = null;
    private Integer type = null;
    private Double num = null;
    private Double price = null;
}
