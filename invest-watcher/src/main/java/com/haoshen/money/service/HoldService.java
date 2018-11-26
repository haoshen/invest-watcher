package com.haoshen.money.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.haoshen.money.entity.Hold;
import com.haoshen.money.mapper.HoldMapper;

@ComponentScan({"com.haoshen.money.mapper"})
@Service("holdService")
public class HoldService {

    @Resource
    private HoldMapper holdMapper;

    public Boolean insert(Hold hold) {
        return holdMapper.insert(hold) == 1;
    }

    public Boolean update(Hold hold) {
        return holdMapper.update(hold) == 1;
    }

    public Hold getById(Integer id) {
        return holdMapper.getById(id);
    }

    public List<Hold> getHoldByCondition(Integer userId, String investId, Integer direction, Integer status) {
        return holdMapper.getHoldByCondition(userId, investId, direction, status);
    }
}
