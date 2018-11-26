package com.haoshen.money.controler;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haoshen.money.dto.ResultMessageDto;
import com.haoshen.money.entity.User;
import com.haoshen.money.manager.UserManager;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserManager userManager;

    @RequestMapping(value="/verify", method = RequestMethod.GET)
    public ResultMessageDto<User> userVerify(@RequestParam String name, @RequestParam String password) {
        ResultMessageDto<User> result = new ResultMessageDto();
        result.setResult(userManager.verifyUser(name, password));
        return result;
    }

    @RequestMapping(value="/get", method = RequestMethod.GET)
    public ResultMessageDto<List<User>> getAll(@RequestParam String root) {
        ResultMessageDto<List<User>> result = new ResultMessageDto();
        result.setResult(userManager.getAllUsers(root));
        return result;
    }

    @RequestMapping(value="/update", method = RequestMethod.GET)
    public ResultMessageDto<Boolean> updateUser(@RequestParam String root,
                                                @RequestParam String name,
                                                @RequestParam(required = false) String password,
                                                @RequestParam(required = false) Integer status) {
        ResultMessageDto<Boolean> result = new ResultMessageDto();
        result.setResult(userManager.updateUser(root, name, password, status));
        return result;
    }

    @RequestMapping(value="/add", method = RequestMethod.GET)
    public ResultMessageDto<Boolean> addUser(@RequestParam String root,
                                                @RequestParam String name,
                                                @RequestParam String password) {
        ResultMessageDto<Boolean> result = new ResultMessageDto();
        result.setResult(userManager.addUser(root, name, password));
        return result;
    }

}
