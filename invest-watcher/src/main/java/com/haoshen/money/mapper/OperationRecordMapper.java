package com.haoshen.money.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.haoshen.money.entity.OperationRecord;

@Mapper
public interface OperationRecordMapper {

    public Integer insert(OperationRecord operationRecord);

    public OperationRecord getById(@Param(value = "id") Integer id);

    public List<OperationRecord> getByUserId(@Param(value = "id") Integer userId);
}
