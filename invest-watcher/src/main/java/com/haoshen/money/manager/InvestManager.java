package com.haoshen.money.manager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.haoshen.money.dto.AccountDto;
import com.haoshen.money.dto.HoldDto;
import com.haoshen.money.dto.HoldOperRecordDto;
import com.haoshen.money.entity.Hold;
import com.haoshen.money.entity.OperationRecord;
import com.haoshen.money.entity.RealTimeMarket;
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

    // 展示实时行情
    public List<RealTimeMarket> getRealTimeMarket() {
        return realTimeMarketService.getAll();
    }

    // 展示实时仓位
    public List<Hold> getCurrentHolds(Integer userId) {
        if (userId == null) {
            return null;
        }
        return holdService.getHoldByCondition(userId, null, null, 0);
    }

    // 展示所有仓位
    public List<HoldDto> getAllHolds(Integer userId) {
        if (userId == null) {
            return null;
        }
        List<HoldDto> holdDtos = new ArrayList<>();
        List<Hold> holds = holdService.getHoldByCondition(userId, null, null, null);
        for(Hold hold : holds) {
            HoldDto dto = new HoldDto();
            dto.setHold(hold);
            dto.setRecords(string2HoldOperRecordDto(hold.getRecords()));
            holdDtos.add(dto);
        }
        return holdDtos;
    }

    // 更新持仓备注
    public Boolean updateHoldComment(Integer userId, Integer holdId, String comment) {
        if (userId == null) {
            return false;
        }
        Hold hold = new Hold();
        hold.setId(holdId);
        hold.setComment(comment);
        return holdService.update(hold);
    }


    // 处理仓位操作，包括：做多开仓、做多平仓、做空开仓、做空平仓
    public Boolean processAccount(AccountDto accountDto) {
        if (accountDto == null || accountDto.getUserId() == null || accountDto.getDirection() == null ||
                accountDto.getInvestId() == null || accountDto.getNum() == null ||
                accountDto.getPrice() == null || accountDto.getType() == null) {
            return false;
        }
        Integer userId = accountDto.getUserId();
        Integer direction = accountDto.getDirection();
        Integer inverstId = accountDto.getInvestId();
        Integer type = accountDto.getType();
        double num = accountDto.getNum();
        double price = accountDto.getPrice();

        // 根据操作品种和开仓方向获取尚未结束的持仓
        List<Hold> holds = holdService.getHoldByCondition(userId, inverstId, direction, 0);

        Integer holdId;
        if (holds != null && !holds.isEmpty()) {            // 如有返回，则表示已有记录，之后需要更新该持仓记录
            Hold hold = holds.get(0);
            if(!processHold(hold , accountDto)) {
                return false;
            }
            holdService.update(hold);
            holdId = hold.getId();
        } else {                                            // 如未返回，则需要创建新的持仓纪录
            Hold hold = new Hold();
            if(!processHold(hold , accountDto)) {
                return false;
            }
            holdService.insert(hold);
            holdId = hold.getId();
        }

        //新增操作记录
        OperationRecord operationRecord = new OperationRecord();
        operationRecord.setUserId(userId);
        operationRecord.setInvestId(inverstId);
        operationRecord.setOperDirection(direction);
        operationRecord.setOperNum(num);
        operationRecord.setOperType(type);
        operationRecord.setOperPrice(price);
        operationRecord.setHoldId(holdId);
        operationRecordService.insert(operationRecord);
        return true;
    }


    // 计算持仓详情
    private Boolean processHold(Hold hold, AccountDto accountDto) {
        // 计算均价、数量、利润等
        double currentNum = hold.getCurrentNum() != null ? hold.getCurrentNum() : 0;
        double currentPrice = hold.getCurrentPrice() != null ? hold.getCurrentPrice() : 0;
        double operNum = accountDto.getNum();
        double operPrice = accountDto.getPrice();
        double operProfit = 0;
        double newNum = 0;
        double newPrice = 0;
        int type = accountDto.getType();
        int direction = accountDto.getDirection();
        if (type == 0) {                        //开仓操作（包括做空开仓或做多开仓），开仓操作没有利润，仓位会增加，价格为平均价格
            newNum = currentNum + operNum;
            if (newNum == 0) {
                newPrice = 0;
            }
            newPrice = (currentNum * currentPrice + operNum * operPrice) / newNum;
            operProfit = 0;
        } else {                                //平仓操作（包括做多平仓或做），平仓操作会影响利润、仓位、价格
            // 仓位不足，无法平仓
            if (operNum > currentNum) {
                return false;
            }
            newNum = currentNum - operNum;
            if (newNum == 0) {                  // 操作之后，仓位为0
                newPrice = 0;
                if (direction == 0) {           // 做多平仓，之后
                    operProfit = operNum * (operPrice - currentPrice);
                } else {                        // 做空平仓
                    operProfit = operNum * (currentPrice - operPrice);
                }
            } else {                            //操作之后，还有剩余仓位
                if (direction == 0) {           // 做多平仓
                    newPrice = (operNum * operPrice - currentNum * currentPrice) / newNum;
                    operProfit = operNum * (operPrice - currentPrice);
                } else {                        // 做空平仓
                    newPrice = (operNum * operPrice - currentNum * currentPrice) / newNum;
                    operProfit = operNum * (currentPrice - operPrice);
                }
            }

        }
        double currentProfit = hold.getProfit() != null ? hold.getProfit() : 0;
        double newProfit = currentProfit + operProfit;

        // 设置持仓中的操作记录
        List<HoldOperRecordDto> recordsDto = string2HoldOperRecordDto(hold.getRecords());
        if (recordsDto == null) {
            recordsDto = new ArrayList<>();
        }
        HoldOperRecordDto horDto = new HoldOperRecordDto();
        horDto.setCurrentNum(currentNum);
        horDto.setCurrentPrice(currentPrice);
        horDto.setCurrentProfit(currentProfit);
        horDto.setOperNum(operNum);
        horDto.setOperPrice(operPrice);
        horDto.setOperProfit(operProfit);
        horDto.setOperType(accountDto.getType());
        horDto.setOperTime(TimeUtil.getCurrentDateTimeStr());
        recordsDto.add(horDto);

        // 更新持仓记录
        hold.setUserId(accountDto.getUserId());
        hold.setInvestId(accountDto.getInvestId());
        hold.setDirection(accountDto.getDirection());
        hold.setRecords(holdOperRecordDto2String(recordsDto));
        hold.setCurrentPrice(newPrice);
        hold.setCurrentNum(newNum);
        hold.setProfit(newProfit);
        hold.setStatus(newNum > 0 ? 0 : 1);
        return true;
    }

    // 持仓记录中的操作记录，在不同类型中的相互转换
    private List<HoldOperRecordDto> string2HoldOperRecordDto(String recordStr) {
        if (recordStr == null || "".equals(recordStr)) {
            return null;
        }
        return JSONObject.parseArray(recordStr, HoldOperRecordDto.class);
    }

    private String holdOperRecordDto2String(List<HoldOperRecordDto> recordsDto) {
        if (recordsDto == null || recordsDto.isEmpty()) {
            return null;
        }
        return JSONObject.toJSONString(recordsDto);
    }
}
