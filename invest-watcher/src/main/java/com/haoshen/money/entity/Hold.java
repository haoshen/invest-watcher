package com.haoshen.money.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Hold implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer userId;
    private Integer investId;
    private Integer direction;
    private Double currentNum;
    private Double currentPrice;
    private Double profit;
    private Integer status;
    private String records;
    private String comment;
    private Date createdAt;
    private Date updatedAt;
}
