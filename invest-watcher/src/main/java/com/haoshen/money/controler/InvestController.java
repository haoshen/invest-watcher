package com.haoshen.money.controler;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.haoshen.money.dto.AccountDto;
import com.haoshen.money.dto.ResultMessageDto;
import com.haoshen.money.manager.InvestManager;

@CrossOrigin
@RestController
@RequestMapping("/invest")
public class InvestController {

    @Resource
    private InvestManager investManager;

    @RequestMapping(value = "/allHolds")
    public JSONObject getAllHolds(@RequestParam Integer userId,
                                   @RequestParam(required = false) Integer limit,
                                   @RequestParam(required = false) Integer offset) {
        return investManager.getAllHolds(userId, limit, offset);
    }

    @RequestMapping(value = "/holdOnHolds")
    public JSONObject getHoldOnHolds(@RequestParam Integer userId,
                                  @RequestParam(required = false) Integer limit,
                                  @RequestParam(required = false) Integer offset,
                                  @RequestParam(required = false) Integer status) {
        return investManager.getHoldOnHolds(userId, limit, offset, status);
    }

    @RequestMapping(value = "/updateHoldComment")
    public ResultMessageDto<Boolean> updateHoldComment(@RequestParam Integer userId,
                                                       @RequestParam Integer holdId,
                                                       @RequestParam String comment) {
        ResultMessageDto<Boolean> result = new ResultMessageDto();
        result.setResult(investManager.updateHoldComment(userId, holdId, comment));
        return result;
    }

    @RequestMapping(value = "/processAccount")
    public ResultMessageDto<Boolean> updateHoldComment(@RequestParam Integer userId,
                                                       @RequestParam Integer direction,
                                                       @RequestParam String investId,
                                                       @RequestParam Integer type,
                                                       @RequestParam Float num,
                                                       @RequestParam Float price) {
        ResultMessageDto<Boolean> result = new ResultMessageDto();
        AccountDto accountDto = new AccountDto();
        accountDto.setUserId(userId);
        accountDto.setInvestId(investId);
        accountDto.setDirection(direction);
        accountDto.setType(type);
        accountDto.setNum(num);
        accountDto.setPrice(price);
        result.setResult(investManager.processAccount(accountDto));
        return result;
    }

}
