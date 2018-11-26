package com.haoshen.money;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.haoshen.money.service.MartekPriceService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class InvestWatcherTest {

    @Resource
    MartekPriceService martekPriceService;

    @Test
    public void test() {
        getAllRealTimeMarkes();
    }

    private void getAllRealTimeMarkes() {
        System.out.println(JSON.toJSON(martekPriceService.getLatestRecords("invest_market_WTICO", 100)));
    }

}
