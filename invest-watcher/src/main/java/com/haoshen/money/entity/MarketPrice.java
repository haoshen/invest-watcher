package com.haoshen.money.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class MarketPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Float start;
    private Float end;
    private Float top;
    private Float bottom;
    private Date startTime;
    private Date endTime;
    private Date createdAt;

}
