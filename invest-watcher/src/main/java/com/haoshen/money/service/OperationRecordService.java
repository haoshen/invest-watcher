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

    public Boolean insert(OperationRecord operationRecord) {
        return operationRecordMapper.insert(operationRecord) == 1;
    }

    public OperationRecord getById(Integer id) {
        return operationRecordMapper.getById(id);
    }

    public List<OperationRecord> getByUserId(Integer userId) {
        return operationRecordMapper.getByUserId(userId);
    }
}
