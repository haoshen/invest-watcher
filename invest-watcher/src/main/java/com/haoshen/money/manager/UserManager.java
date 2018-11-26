package com.haoshen.money.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.haoshen.money.entity.User;
import com.haoshen.money.service.UserService;
import com.haoshen.money.utils.ConstUtil;

@Service("userManager")
public class UserManager {

    @Resource
    private UserService userService;

    public User verifyUser(String name, String password) {
        if (name == null || password == null) {
            return null;
        }
        User user = userService.getByNameAndPassword(name, password);
        return user;
    }

    public Boolean updateUser(String root, String name, String password, Integer status) {
        if (!ConstUtil.checkRoot(root)) {
            return false;
        }
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setStatus(status);
        return userService.update(user);
    }

    public Boolean addUser(String root, String name, String password) {
        if (!ConstUtil.checkRoot(root)) {
            return false;
        }
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        return userService.insert(user);
    }

    public List<User> getAllUsers(String root) {
        if (!ConstUtil.checkRoot(root)) {
            return null;
        }
        return userService.getAll();
    }



}
