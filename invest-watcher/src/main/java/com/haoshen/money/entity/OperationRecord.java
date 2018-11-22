package com.haoshen.money.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class OperationRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int investId;
    private int operDirection;
    private int operType;
    private double operNum;
    private double operPrice;
    private Date operDate;
    private int holdId;

}
