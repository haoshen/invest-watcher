package com.haoshen.money.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class RealTimeMarket implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer investId;
    private String name;
    private Double buy;
    private Double sell;
    private Date createdAt;
    private Date updatedAt;
}
