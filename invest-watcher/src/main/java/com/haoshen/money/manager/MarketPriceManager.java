package com.haoshen.money.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.haoshen.money.entity.MarketPrice;
import com.haoshen.money.service.MartekPriceService;

@Service("marketPriceManager")
public class MarketPriceManager {

    @Resource
    private MartekPriceService martekPriceService;

    public void insert(String tableName, MarketPrice marketPrice) {
        if (marketPrice == null || marketPrice.getStart() == null || marketPrice.getEnd() == null ||
                marketPrice.getTop() == null || marketPrice.getBottom() == null ||
                marketPrice.getStartTime() == null || marketPrice.getEndTime() == null) {
            return;
        }
        martekPriceService.insert(tableName, marketPrice);
    }

    public List<MarketPrice> getLatestRecords(String tableName, Integer num) {
        return martekPriceService.getLatestRecords(tableName, num);
    }


}
