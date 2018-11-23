package com.haoshen.money.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.haoshen.money.entity.User;

@Mapper
public interface UserMapper {

    public Integer insert(User user);

    public Integer update(User user);

    public List<User> getAll();

    public User getByNameAndPassword(@Param(value = "name") String name, @Param(value = "password") String password);

}
