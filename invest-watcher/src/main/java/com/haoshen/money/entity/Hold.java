package com.haoshen.money.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Hold implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer userId;
    private String investId;
    private Integer direction;
    private Float currentNum;
    private Float currentPrice;
    private Float profit;
    private Integer status;
    private String records;
    private String comment;
    private Date createdAt;
    private Date updatedAt;
}
