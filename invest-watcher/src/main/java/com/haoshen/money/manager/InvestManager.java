package com.haoshen.money.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.haoshen.money.crawler.GlobalMarket;
import com.haoshen.money.dto.AccountDto;
import com.haoshen.money.dto.HoldAndProfitDto;
import com.haoshen.money.dto.HoldDto;
import com.haoshen.money.dto.HoldOperRecordDto;
import com.haoshen.money.dto.RealTimeMarketDto;
import com.haoshen.money.entity.Hold;
import com.haoshen.money.entity.OperationRecord;
import com.haoshen.money.service.HoldService;
import com.haoshen.money.service.OperationRecordService;
import com.haoshen.money.utils.TimeUtil;

@Service("investManager")
@DependsOn("globalMarket")
public class InvestManager {

    @Resource
    private HoldService holdService;

    @Resource
    private OperationRecordService operationRecordService;

    @Resource
    private GlobalMarket globalMarket;

    // 各个用户对应的实时持仓缓存
    private Map<Integer, List<Hold>> currentHoldsCacheMap = new HashMap<>();

    // 各个用户对应的持仓是否需要更新状态
    private Map<Integer, Boolean> shouldHoldCacheUpdateMap = new HashMap<>();

    // 各个用户对应的total
    private Map<Integer, Integer> holdCacheTotalMap = new HashMap<>();

    // 展示所有历史仓位
    public JSONObject getAllHolds(Integer userId, Integer limit, Integer offset) {
        JSONObject jsonObject = new JSONObject();
        if (userId == null) {
            return null;
        }
        List<HoldDto> holdDtos = new ArrayList<>();
        List<Hold> holds = holdService.getHoldByCondition(userId, null, null, null, true, limit, offset);
        for(Hold hold : holds) {
            HoldDto dto = new HoldDto();
            dto.setProfit(hold.getProfit());
            dto.setCurrentPrice(hold.getCurrentPrice());
            dto.setCurrentNum(hold.getCurrentNum());
            dto.setId(hold.getId());
            //将code改为name
            dto.setInvestId(globalMarket.getNameByCode(hold.getInvestId()));
            dto.setComment(hold.getComment());
            dto.setRecords(hold.getRecords());
            dto.setUserId(hold.getUserId());
            dto.setDirection(hold.getDirection());
            dto.setStatus(hold.getStatus());
            holdDtos.add(dto);
        }
        jsonObject.put("rows", holdDtos);
        jsonObject.put("total", holdService.getCountByCondition(userId, null, null, null));
        return jsonObject;
    }

    // 展示实时仓位及实时行情利润
    public JSONObject getHoldOnHolds(Integer userId, Integer limit, Integer offset, Integer status) {
        if (userId == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        List<HoldAndProfitDto> holdAndProfitDtos = new ArrayList<>();
        Boolean shouldHoldsCacheUpdated = shouldHoldCacheUpdateMap.get(userId);
        // 判断是否需要更新缓存
        List<Hold> currentHoldsCache = currentHoldsCacheMap.get(userId);
        Integer total = holdCacheTotalMap.get(userId);
        if (shouldHoldsCacheUpdated == null || shouldHoldsCacheUpdated) {
            currentHoldsCache = holdService.getHoldByCondition(userId, null, null, status, true, limit,
                    offset);
            total = holdService.getCountByCondition(userId, null, null, status);
            currentHoldsCacheMap.put(userId, currentHoldsCache);
            holdCacheTotalMap.put(userId, total);
            shouldHoldCacheUpdateMap.put(userId, false);
        }
        Map<String, RealTimeMarketDto> realTimeMarkets = globalMarket.getAllRealTimeMarkets();
        if (currentHoldsCache != null && realTimeMarkets != null) {
            for(Hold hold : currentHoldsCache) {
                HoldAndProfitDto dto = new HoldAndProfitDto();
                dto.setInvestId(hold.getInvestId());
                dto.setId(hold.getId());
                dto.setUserId(hold.getUserId());
                dto.setDirection(hold.getDirection());
                dto.setCurrentNum(hold.getCurrentNum());
                dto.setCurrentPrice(hold.getCurrentPrice());
                dto.setRecords(hold.getRecords());
                dto.setProfit(hold.getProfit());
                dto.setComment(hold.getComment());
                // 计算实时利润
                RealTimeMarketDto realTimeMarket = realTimeMarkets.get(hold.getInvestId());
                if (realTimeMarket != null) {
                    Float buy = realTimeMarket.getBuy();
                    Float sell = realTimeMarket.getSell();
                    if (buy != null && sell != null) {
                        dto.setName(realTimeMarket.getName());
                        dto.setSell(sell);
                        dto.setBuy(buy);
                        Float profit;
                        if (hold.getDirection() == 0) {     //做多
                            profit = (buy - hold.getCurrentPrice()) * hold.getCurrentNum();
                        } else {                            //做空
                            profit = (hold.getCurrentPrice() - sell) * hold.getCurrentNum();
                        }
                        dto.setVirtualProfit(profit);
                        dto.setUpdatedAt(TimeUtil.getDateTimeStr(realTimeMarket.getUpdatedTime()));
                    }
                }
                holdAndProfitDtos.add(dto);
            }
        }
        jsonObject.put("rows", holdAndProfitDtos);
        jsonObject.put("total", total);
        return jsonObject;
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
                accountDto.getInvestId() == null || accountDto.getNum() == null || accountDto.getNum() <=0 ||
                accountDto.getPrice() == null || accountDto.getPrice() <= 0 || accountDto.getType() == null) {
            return false;
        }
        Integer userId = accountDto.getUserId();
        Integer direction = accountDto.getDirection();
        String inverstId = accountDto.getInvestId();
        Integer type = accountDto.getType();
        float num = accountDto.getNum();
        float price = accountDto.getPrice();

        // 根据操作品种和开仓方向获取尚未结束的持仓
        List<Hold> holds = holdService.getHoldByCondition(userId, inverstId, direction, 0, false, null, null);

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

        //通知持仓缓存进行更新
        shouldHoldCacheUpdateMap.put(userId, true);

        return true;
    }


    // 计算持仓详情
    private Boolean processHold(Hold hold, AccountDto accountDto) {
        // 计算均价、数量、利润等
        float currentNum = hold.getCurrentNum() != null ? hold.getCurrentNum() : 0;
        float currentPrice = hold.getCurrentPrice() != null ? hold.getCurrentPrice() : 0;
        float operNum = accountDto.getNum();
        float operPrice = accountDto.getPrice();
        float operProfit = 0;
        float newNum = 0;
        float newPrice = 0;
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
                    newPrice = currentPrice;    // 平仓不会影响价格
                    operProfit = operNum * (operPrice - currentPrice);
                } else {                        // 做空平仓
                    newPrice = currentPrice;
                    operProfit = operNum * (currentPrice - operPrice);
                }
            }

        }
        float currentProfit = hold.getProfit() != null ? hold.getProfit() : 0;
        float newProfit = currentProfit + operProfit;

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
