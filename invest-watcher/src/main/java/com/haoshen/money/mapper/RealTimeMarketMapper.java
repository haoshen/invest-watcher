package com.haoshen.money.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.haoshen.money.entity.RealTimeMarket;

@Mapper
public interface RealTimeMarketMapper {

    public Integer insert(RealTimeMarket realTimeMarket);

    public Integer update(RealTimeMarket realTimeMarket);

    public RealTimeMarket getById(@Param(value = "id") Integer id);

    public List<RealTimeMarket> getAll();
}
