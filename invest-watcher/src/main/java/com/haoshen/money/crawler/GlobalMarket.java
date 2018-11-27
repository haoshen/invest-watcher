package com.haoshen.money.crawler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.haoshen.money.dto.RealTimeMarketDto;
import com.haoshen.money.entity.MarketPrice;
import com.haoshen.money.manager.MarketPriceManager;

@Service("globalMarket")
public class GlobalMarket implements InitializingBean {

    @Resource
    private MarketPriceManager marketPriceManager;

    // 投资品种总数
    private static int allInvestSize = 12;

    // 所有投资品种代码
    private static String[] allInvestCodes = {"AURMB", "AGRMB", "XAU", "XAG", "WTICO", "BCO",
            "WTIUS", "WTICN", "BRENTUS", "BRENTCN", "GASUS", "GASCN"};

    // 所有投资品种名称
    private static  String[] allInvestNames = {"纸黄金", "纸白银", "现货黄金", "现货白银",
            "现货北美油", "现货国际油", "美元北美油",  "人民币北美油", "美元国际油", "人民币国际油", "美元天然气", "人民币天然气"};

    private static Map<String, String> codeNameMap = new HashMap<>();;

    // 实时行情，供前端调用
    private Map<String, RealTimeMarketDto> realTimeMarketDtoMap = new HashMap<>();

    // 历史行情，供数据库调用，保存的价格为sell价格
    private Map<String, MarketPrice> historyMarketPriceMap = new HashMap<>();

    // 数据库更新时间间隔，目前为分级别
    private static long DATEBASE_UPDATED_INTERVAL = 60 * 1000;

    @Override
    public void afterPropertiesSet() {
        init();
    }

    private void init() {
        for(int i = 0; i < allInvestSize; i++) {
            // 初始化实时行情
            String code = allInvestCodes[i];
            String name = allInvestNames[i];
            RealTimeMarketDto realTimeMarket = new RealTimeMarketDto();
            realTimeMarket.setCode(code);
            realTimeMarket.setName(name);
            realTimeMarket.setUpdatedTime(new Date());
            realTimeMarketDtoMap.put(code, realTimeMarket);

            // 初始化历史行情
            MarketPrice marketPrice  = new MarketPrice();
            marketPrice.setStart(0f);
            marketPrice.setStartTime(new Date());
            marketPrice.setBottom(Float.MAX_VALUE);
            marketPrice.setTop(Float.MIN_VALUE);
            historyMarketPriceMap.put(code, marketPrice);

            //初始化code和name对应关系
            codeNameMap.put(code, name);
        }
    }

    public String getNameByCode(String code) {
        if(code == null) {
            return code;
        }
        String name = codeNameMap.get(code);
        if (name == null) {
            return code;
        }
        return name;
    }

    public RealTimeMarketDto getRealTimeMarketByCode(String code) {
        return this.realTimeMarketDtoMap.get(code);
    }

    public Map<String, RealTimeMarketDto> getAllRealTimeMarkets() {
        return this.realTimeMarketDtoMap;
    }

    public List<RealTimeMarketDto> getAllRealTimeMarketList(Integer limit, Integer offset) {
        List<RealTimeMarketDto> list = new ArrayList<>();
        if (this.realTimeMarketDtoMap != null) {
            for (Map.Entry<String, RealTimeMarketDto> entry : this.realTimeMarketDtoMap.entrySet()) {
                list.add(entry.getValue());
            }
        }
        return list;
    }

    public Map<String, MarketPrice> getAllHistoryMarkets() {
        return this.historyMarketPriceMap;
    }

    // 更新行情
    public void updateMarket(String code, Float buy, Float sell, Date time) {
        if (code == null || buy == null || sell == null || time == null) {
            return;
        }
        RealTimeMarketDto realTimeMarket = realTimeMarketDtoMap.get(code);
        Date realTimeMarketUpdatedTime = realTimeMarket.getUpdatedTime();
        if (realTimeMarket == null || realTimeMarketUpdatedTime == null) {
            return;
        }
        if (time.after(realTimeMarketUpdatedTime)) {
            // 更新实时行情
            realTimeMarket.setBuy(buy);
            realTimeMarket.setSell(sell);
            realTimeMarket.setUpdatedTime(time);
            realTimeMarketDtoMap.put(code, realTimeMarket);

            // 更新历史行情
            MarketPrice marketPrice = historyMarketPriceMap.get(code);
            Date marketPirceStartTime = marketPrice.getStartTime();
            if (marketPrice == null || marketPirceStartTime == null) {
                return;
            }
            if (time.after(marketPirceStartTime)) {
                // 更新时间周期内的最高价和最低价
                marketPrice.setBottom(Math.min(sell, marketPrice.getBottom()));
                marketPrice.setTop(Math.max(sell, marketPrice.getTop()));
                // 超过更新间隔阈值，则需要更新数据库
                if (time.getTime() - marketPirceStartTime.getTime() >= DATEBASE_UPDATED_INTERVAL) {
                    marketPrice.setEnd(sell);
                    marketPrice.setEndTime(time);
                    // 更新数据库
                    marketPriceManager.insert(getMarketPriceTableName(code), marketPrice);
                    // 重置属性
                    marketPrice.setStart(sell);
                    marketPrice.setStartTime(time);
                    marketPrice.setTop(sell);
                    marketPrice.setBottom(sell);
                }
                historyMarketPriceMap.put(code, marketPrice);
            }

        }
    }

    private String getMarketPriceTableName(String code) {
        return "invest_market_" + code;
    }

}
