package com.haoshen.money.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.haoshen.money.dto.Account;
import com.haoshen.money.dto.HoldRecord;
import com.haoshen.money.entity.Hold;
import com.haoshen.money.service.HoldService;
import com.haoshen.money.service.OperationRecordService;
import com.haoshen.money.service.RealTimeMarketService;
import com.haoshen.money.utils.TimeUtil;

@Service("investManager")
public class InvestManager {

    @Resource
    private HoldService holdService;

    @Resource
    private RealTimeMarketService realTimeMarketService;

    @Resource
    private OperationRecordService operationRecordService;

    // 开仓
    public boolean openAccount(Account account, String errorMessage) {
        if (account.getDirection() == null || account.getInvestId() == null ||
                account.getNum() == null || account.getPrice() == null ||
                account.getType() == null) {
            return false;
        }
        // 根据操作品种和开仓方向获取尚未结束的持仓
        Hold hold = holdService.getCarringOnHold(account.getInvestId(), account.getDirection());
        processHold(hold, account);
    }

    private void processHold(Hold hold, Account account) {
        if (hold == null) {         //新的开仓
            hold = new Hold();
            hold.setCurrentNum(account.getNum());
            hold.setCurrentPrice(account.getNum());
            hold.setDirection(account.getDirection());
            hold.setProfit(0);
            hold.setStatus(0);
            hold.setVestId(account.getInvestId());
        } else {                    //已有仓位上加仓

        }
        HoldRecord holdRecord = JSON.parseObject(hold.getRecords(), HoldRecord.class);
        if (holdRecord == null) {
            holdRecord = new HoldRecord();
        }
        if (hold != null) {
            holdRecord.setCurrentNum(hold.getCurrentNum());
            holdRecord.setCurrentPrice(hold.getCurrentPrice());
            holdRecord.setCurrentProfit(hold.getProfit());
            holdRecord.setOperTime(TimeUtil.getCurrentDateTimeStr());
            holdRecord.setOperNum(account.getNum());
            holdRecord.setOperPrice(account.getPrice());
            holdRecord.setOperType(account.getType());
        }
        return holdRecord;
    }
}
