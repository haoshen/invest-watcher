package com.haoshen.money.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.haoshen.money.entity.RealTimeMarket;
import com.haoshen.money.mapper.RealTimeMarketMapper;

@ComponentScan({"com.haoshen.money.mapper"})
@Service("realTimeMarketService")
public class RealTimeMarketService {

    @Resource
    private RealTimeMarketMapper realTimeMarketMapper;

    public void insert(RealTimeMarket realTimeMarket) {
        realTimeMarketMapper.insert(realTimeMarket);
    }

    public void update(RealTimeMarket realTimeMarket) {
        realTimeMarketMapper.update(realTimeMarket);
    }

    public RealTimeMarket getById(Integer id) {
        return realTimeMarketMapper.getById(id);
    }

    public List<RealTimeMarket> getAll() {
        return realTimeMarketMapper.getAll();
    }
}
