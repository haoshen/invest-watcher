package com.haoshen.money.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.haoshen.money.entity.Hold;

@Mapper
public interface HoldMapper {

    public void insert(Hold hold);

    public void update(Hold hold);

    public Hold getById(Integer id);

    public List<Hold> getAll();

    // 获取进行中的持仓
    public Hold getCarringOnHold(@Param("investId") Integer investId,
                                       @Param("direction") Integer direction);
}
