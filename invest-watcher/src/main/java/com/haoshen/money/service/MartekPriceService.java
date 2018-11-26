package com.haoshen.money.service;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.haoshen.money.entity.MarketPrice;
import com.haoshen.money.mapper.MartekPriceMapper;

@ComponentScan({"com.haoshen.money.mapper"})
@Service("marketPriceService")
public class MartekPriceService {

    @Resource
    private MartekPriceMapper martekPriceMapper;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void insert(String tableName, MarketPrice marketPrice) {
        martekPriceMapper.insert(tableName, marketPrice.getStart(), marketPrice.getEnd(),
                marketPrice.getTop(), marketPrice.getBottom(), dateFormat.format(marketPrice.getStartTime()),
                dateFormat.format(marketPrice.getEndTime()));
    }

    public List<MarketPrice> getLatestRecords(String tableName, Integer num) {
        return martekPriceMapper.getLatestRecords(tableName, num);
    }

}
