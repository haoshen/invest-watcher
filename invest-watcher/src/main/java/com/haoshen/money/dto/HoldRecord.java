package com.haoshen.money.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class HoldRecord {

    private List<HoldOperRecord> records = new ArrayList<HoldOperRecord>();

}
