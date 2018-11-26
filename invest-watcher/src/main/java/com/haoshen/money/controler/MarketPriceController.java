package com.haoshen.money.controler;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haoshen.money.crawler.GlobalMarket;
import com.haoshen.money.dto.RealTimeMarketDto;
import com.haoshen.money.dto.ResultMessageDto;
import com.haoshen.money.entity.MarketPrice;
import com.haoshen.money.manager.MarketPriceManager;

@RestController
@RequestMapping("/market")
public class MarketPriceController {

    @Resource
    private MarketPriceManager marketPriceManager;

    @Resource
    private GlobalMarket globalMarket;

    @RequestMapping(value="/current")
    public ResultMessageDto<Map<String, RealTimeMarketDto>> getAllRealTimeMarkets() {
        ResultMessageDto<Map<String, RealTimeMarketDto>> result = new ResultMessageDto();
        result.setResult(globalMarket.getAllRealTimeMarkets());
        return result;
    }

    @RequestMapping(value="/current/{code}")
    public ResultMessageDto<RealTimeMarketDto> getRealTimeMarketByCode(@PathVariable("id") String code) {
        ResultMessageDto<RealTimeMarketDto> result = new ResultMessageDto();
        result.setResult(globalMarket.getRealTimeMarketByCode(code));
        return result;
    }

    @RequestMapping(value="/current/history")
    public ResultMessageDto<Map<String, MarketPrice>> getAllHistoryMarkets() {
        ResultMessageDto<Map<String, MarketPrice>> result = new ResultMessageDto();
        result.setResult(globalMarket.getAllHistoryMarkets());
        return result;
    }

    @RequestMapping(value="/getDatabaseLatestRecords")
    public ResultMessageDto<List<MarketPrice>> getDatabaseLatestRecords(@RequestParam String name, @RequestParam Integer num) {
        ResultMessageDto<List<MarketPrice>> result = new ResultMessageDto();
        if(num > 0 && num <= 200) {
            result.setResult(marketPriceManager.getLatestRecords(name, num));
        }
        return result;
    }


}
