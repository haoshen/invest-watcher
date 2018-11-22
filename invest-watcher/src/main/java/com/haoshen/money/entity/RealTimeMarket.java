package com.haoshen.money.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class RealTimeMarket implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private int investId;
    private String name;
    private double buy;
    private double sell;
    private Date createdAt;
    private Date updatedAt;
}
