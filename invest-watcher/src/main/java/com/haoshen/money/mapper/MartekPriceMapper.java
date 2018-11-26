package com.haoshen.money.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.haoshen.money.entity.MarketPrice;

@Mapper
public interface MartekPriceMapper {

    public Integer insert(@Param("tableName") String tableName,
                          @Param("start")  Float start,
                          @Param("end") Float end,
                          @Param("top") Float top,
                          @Param("bottom") Float bottom,
                          @Param("startTime") String startTime,
                          @Param("endTime") String endTime);

    public List<MarketPrice> getLatestRecords(@Param("tableName") String tableName,
                                              @Param("num") Integer num);

}
