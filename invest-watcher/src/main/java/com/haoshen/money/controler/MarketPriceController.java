package com.haoshen.money.controler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.haoshen.money.crawler.GlobalMarket;
import com.haoshen.money.dto.RealTimeMarketDto;
import com.haoshen.money.dto.ResultMessageDto;
import com.haoshen.money.entity.MarketPrice;
import com.haoshen.money.manager.MarketPriceManager;

@CrossOrigin
@RestController
@RequestMapping("/market")
public class MarketPriceController {

    @Resource
    private MarketPriceManager marketPriceManager;

    @Resource
    private GlobalMarket globalMarket;

    @RequestMapping(value="/current")
    public JSONObject getAllRealTimeMarkets(@RequestParam Integer limit,
                                            @RequestParam Integer offset) {
        List<RealTimeMarketDto> list = globalMarket.getAllRealTimeMarketList(limit, offset);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", list.size());
        Integer finalOffset = offset;
        Integer finalOffsetLimit = offset + limit;
        if(finalOffset >= list.size()) {
            jsonObject.put("rows", new ArrayList<>());
        } else if(finalOffsetLimit > list.size()) {
            finalOffsetLimit = list.size();
            jsonObject.put("rows", list.subList(finalOffset, finalOffsetLimit));
        } else {
            jsonObject.put("rows", list.subList(finalOffset, finalOffsetLimit));
        }
        return jsonObject;
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
