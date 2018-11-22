package com.haoshen.money.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.haoshen.money.entity.RealTimeMarket;

@Mapper
public interface RealTimeMarketMapper {

    public void insert(RealTimeMarket realTimeMarket);

    public void update(RealTimeMarket realTimeMarket);

    public RealTimeMarket getById(Integer id);

    public List<RealTimeMarket> getAll();
}
