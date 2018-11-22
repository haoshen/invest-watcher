package com.haoshen.money.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.haoshen.money.entity.OperationRecord;

@Mapper
public interface OperationRecordMapper {

    public void insert(OperationRecord operationRecord);

    public OperationRecord getById(Integer id);

    public List<OperationRecord> getAll();
}
