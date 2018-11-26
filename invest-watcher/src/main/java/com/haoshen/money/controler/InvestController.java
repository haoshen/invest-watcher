package com.haoshen.money.controler;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haoshen.money.dto.AccountDto;
import com.haoshen.money.dto.HoldDto;
import com.haoshen.money.dto.ResultMessageDto;
import com.haoshen.money.entity.Hold;
import com.haoshen.money.manager.InvestManager;

@RestController
@RequestMapping("/invest")
public class InvestController {

    @Resource
    private InvestManager investManager;

    @RequestMapping(value = "/realTimeMarket")
    public ResultMessageDto<Boolean> getRealTimeMarket() {
        ResultMessageDto<Boolean> result = new ResultMessageDto();
        result.setResult(true);
        return result;
    }

    @RequestMapping(value = "/currentHolds")
    public ResultMessageDto<List<Hold>> getRealTimeHolds(@RequestParam Integer userId) {
        ResultMessageDto<List<Hold>> result = new ResultMessageDto();
        result.setResult(investManager.getCurrentHolds(userId));
        return result;
    }

    @RequestMapping(value = "/allHolds")
    public ResultMessageDto<List<HoldDto>> getAllHolds(@RequestParam Integer userId) {
        ResultMessageDto<List<HoldDto>> result = new ResultMessageDto();
        result.setResult(investManager.getAllHolds(userId));
        return result;
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
