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

    public void insert(Hold hold) {
        holdMapper.insert(hold);
    }

    public void update(Hold hold) {
        holdMapper.update(hold);
    }

    public Hold getById(Integer id) {
        return holdMapper.getById(id);
    }

    public List<Hold> getAll() {
        return holdMapper.getAll();
    }

    // 获取尚未结束的持仓
    public Hold getCarringOnHold(Integer investId, Integer direction) {
        return holdMapper.getCarringOnHold(investId, direction);
    }
}
