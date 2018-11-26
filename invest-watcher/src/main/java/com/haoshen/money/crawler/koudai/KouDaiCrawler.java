package com.haoshen.money.crawler.koudai;

import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haoshen.money.crawler.GlobalMarket;
import com.haoshen.money.utils.SpringContextUtil;

public class KouDaiCrawler implements Job {

    private static Logger log = LoggerFactory.getLogger(KouDaiCrawler.class);

    private static CloseableHttpClient httpclient = HttpClients.createDefault();

    private static String KOUDAI_API = "https://m.sojex.net/api.do/batchQuote?ids=[3,4,12,13,14,15,108,150,152]";

    private static GlobalMarket globalMarket = SpringContextUtil.getBean(GlobalMarket.class);;

    // 执行口袋贵金属抓取任务
    public void execute(JobExecutionContext context) throws JobExecutionException {
        process();
    }

    private void process() {
        long startTime = System.currentTimeMillis();
        // 获取网页接口数据
        HttpGet httpGet = new HttpGet(KOUDAI_API);
        CloseableHttpResponse response = null;
        String result = "";
        try {
            response = httpclient.execute(httpGet);
            HttpEntity entity1 = response.getEntity();
            result = EntityUtils.toString(entity1);
            if (log.isDebugEnabled()) {
                log.debug(EntityUtils.toString(entity1));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            log.info("crawler get web fail");
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    log.error(e.getMessage());
                    log.info("crawler close http response fail");
                }
            }
        }
        long getWebDataTime = System.currentTimeMillis();
        log.info("crawler get web use time " + (getWebDataTime - startTime) + " ms");
        if ("".equals(result)) {
            log.info("crawler no web data got");
            return;
        }
        // 处理网页数据
        processWebData(result);
        long finishTime = System.currentTimeMillis();
        log.info("crawler process web data use time " + (finishTime - getWebDataTime) + " ms");
    }

    private void processWebData(String webData) {
        JSONObject jsonObject = JSONObject.parseObject(webData);
        if(!"OK".equals(jsonObject.getString("desc"))) {
            log.info("web data is not ok");
            return;
        }
        JSONArray dataArray = jsonObject.getJSONArray("data");
        if(dataArray == null || dataArray.isEmpty()) {
            log.info("web data array is not ready");
            return;
        }
        for(int i = 0; i < dataArray.size(); i++) {
            JSONObject data = dataArray.getJSONObject(i);
            String code = data.getString("code");
            Float buy = Float.parseFloat(data.getString("buy"));
            Float sell = Float.parseFloat(data.getString("sell"));
            Date time = data.getDate("updatetime");
//            System.out.println(code + " | " + buy + " | " + sell + " | " + time);
            // 更新行情
            globalMarket.updateMarket(code, buy, sell, time);
        }
    }


    public static void main(String[] args) throws Exception{
        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();
            HttpGet httpGet = new HttpGet(KOUDAI_API);
            CloseableHttpResponse response = null;
            String result = "";
            try {
                response = httpclient.execute(httpGet);
                HttpEntity entity1 = response.getEntity();
                result = EntityUtils.toString(entity1);
                if (log.isDebugEnabled()) {
                    log.debug(EntityUtils.toString(entity1));
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                log.info("crawler get web fail");
            } finally {
                if (response != null) {
                    try {
                        response.close();
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        log.info("crawler close http response fail");
                    }
                }
            }
            System.out.println(System.currentTimeMillis() - startTime);
        }
    }

}
