package com.haoshen.money.dto;

import java.io.Serializable;
import java.util.List;

import com.haoshen.money.entity.Hold;

import lombok.Data;

@Data
public class HoldDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Hold hold;
    private List<HoldOperRecordDto> records;      // 将操作记录由String转为HoldOperRecordDto

}
