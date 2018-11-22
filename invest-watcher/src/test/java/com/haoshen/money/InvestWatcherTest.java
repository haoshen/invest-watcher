package com.haoshen.money;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.haoshen.money.entity.RealTimeMarket;
import com.haoshen.money.service.RealTimeMarketService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class InvestWatcherTest {

    @Resource
    RealTimeMarketService realTimeMarketService;

    @Test
    public void test() {
        getAllRealTimeMarkes();
    }

    private void insertRealTimeMarket() {
        RealTimeMarket realTimeMarket = new RealTimeMarket();
        realTimeMarket.setInvestId(1);
        realTimeMarket.setName("gold");
        realTimeMarket.setBuy(100.5);
        realTimeMarket.setSell(102.11);
        realTimeMarketService.insert(realTimeMarket);
    }

    private void updateRealTimeMarket() {
        RealTimeMarket realTimeMarket = new RealTimeMarket();
        realTimeMarket.setId(1);
        realTimeMarket.setInvestId(1);
        realTimeMarket.setName("sliver");
        realTimeMarket.setBuy(101.5);
        realTimeMarket.setSell(103.11);
        realTimeMarketService.update(realTimeMarket);
    }

    private void getRealTimeMarketById() {
        RealTimeMarket realTimeMarket = realTimeMarketService.getById(1);
        System.out.println(JSON.toJSON(realTimeMarket));
    }

    private void getAllRealTimeMarkes() {
        System.out.println(JSON.toJSON(realTimeMarketService.getAll()));
    }

}
