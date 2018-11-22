package com.haoshen.money.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Hold implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int vestId;
    private int direction;
    private double currentNum;
    private double currentPrice;
    private double profit;
    private int status;
    private String records;
    private String comment;
    private Date createdAt;
    private Date updatedAt;
}
