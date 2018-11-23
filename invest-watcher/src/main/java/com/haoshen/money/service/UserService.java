package com.haoshen.money.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.haoshen.money.entity.User;
import com.haoshen.money.mapper.UserMapper;

@ComponentScan({"com.haoshen.money.mapper"})
@Service("userService")
public class UserService {

    @Resource
    private UserMapper userMapper;

    public Boolean insert(User user) {
        return userMapper.insert(user) == 1;
    }

    public Boolean update(User user) {
        return userMapper.update(user) == 1;
    }

    public List<User> getAll() { return userMapper.getAll();}

    public User getByNameAndPassword(String name, String password) {
        return userMapper.getByNameAndPassword(name, password);
    }
}
