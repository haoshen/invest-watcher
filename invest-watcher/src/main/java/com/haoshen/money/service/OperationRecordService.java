package com.haoshen.money.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.haoshen.money.entity.OperationRecord;
import com.haoshen.money.mapper.OperationRecordMapper;

@ComponentScan({"com.haoshen.money.mapper"})
@Service("operationRecordService")
public class OperationRecordService {

    @Resource
    private OperationRecordMapper operationRecordMapper;

    public void insert(OperationRecord operationRecord) {
        operationRecordMapper.insert(operationRecord);
    }

    public OperationRecord getById(Integer id) {
        return operationRecordMapper.getById(id);
    }

    public List<OperationRecord> getAll() {
        return operationRecordMapper.getAll();
    }
}
