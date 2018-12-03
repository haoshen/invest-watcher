package com.haoshen.money.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.haoshen.money.entity.Hold;

@Mapper
public interface HoldMapper {

    public Integer insert(Hold hold);

    public Integer update(Hold hold);

    public Hold getById(@Param(value = "id") Integer id);

    public List<Hold> getHoldByCondition(@Param(value = "userId") Integer userId, @Param(value = "investId") String investId,
                                         @Param(value = "direction") Integer direction, @Param(value = "status") Integer status,
                                         @Param(value = "offset") Integer offset, @Param(value = "rows") Integer rows);

    public List<Hold> getHoldByConditionWithoutRecords(@Param(value = "userId") Integer userId, @Param(value = "investId") String investId,
                                         @Param(value = "direction") Integer direction, @Param(value = "status") Integer status,
                                         @Param(value = "offset") Integer offset, @Param(value = "rows") Integer rows);

    public Integer getCountByCondition(@Param(value = "userId") Integer userId, @Param(value = "investId") String investId,
                                         @Param(value = "direction") Integer direction, @Param(value = "status") Integer status);
}
