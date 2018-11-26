package com.haoshen.money.crawler.koudai;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.haoshen.money.crawler.GlobalMarket;
import com.haoshen.money.utils.SpringContextUtil;

// 爬取能源行情
public class KouDaiEnergyCrawler implements Job {

    private static Logger log = LoggerFactory.getLogger(KouDaiEnergyCrawler.class);

    private static CloseableHttpClient httpclient = HttpClients.createDefault();

    private static String KOUDAI_API = "https://mybank.icbc.com.cn/ctp/ctpservlet/EbdpAjaxServlet";

    private static GlobalMarket globalMarket = SpringContextUtil.getBean(GlobalMarket.class);

    private static Map<String, String> idCodeMap;

    private static HttpPost httpPost;

    static {
        idCodeMap = new HashMap<>();
        idCodeMap.put("130060153591", "WTICN");     //人民币北美油
        idCodeMap.put("130060153592", "BRENTCN");   //人民币国际油
        idCodeMap.put("130060153593", "WTIUS");     //美元北美油
        idCodeMap.put("130060153594", "BRENTUS");   //美元国际油
        idCodeMap.put("130060176591", "GASCN");     //人民币天然气
        idCodeMap.put("130060176592", "GASUS");     //美元天然气
        // 设置post参数
        List<NameValuePair> postParamsList = new ArrayList<>();
        postParamsList.add(new BasicNameValuePair("Area_code", "0200"));
        postParamsList.add(new BasicNameValuePair("BUSITYPE", "18"));
        postParamsList.add(new BasicNameValuePair("AcctOTOpenPro",
                "130060153591|130060153592|130060153593|130060153594|130060176591|130060176592"));
        postParamsList.add(new BasicNameValuePair("AcctOTOpenProSX",
                "130060153591|130060153592|130060153593|130060153594|130060176591|130060176592"));
        postParamsList.add(new BasicNameValuePair("trademode", "1"));
        postParamsList.add(new BasicNameValuePair("tranCode", "A00520"));
        postParamsList.add(new BasicNameValuePair("isforeignin", "2"));
        postParamsList.add(new BasicNameValuePair("isFirstTime", "1"));
        postParamsList.add(new BasicNameValuePair("proIdsIn", ""));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParamsList, Charset.defaultCharset());
        httpPost = new HttpPost(KOUDAI_API);
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setEntity(entity);
    }

    // 执行口袋贵金属抓取任务
    public void execute(JobExecutionContext context) throws JobExecutionException {
        process();
    }

    private void process() {
        long startTime = System.currentTimeMillis();
        // 获取网页接口数据
        CloseableHttpResponse response = null;
        String result = "";
        try {
            response = httpclient.execute(httpPost);
            HttpEntity entity1 = response.getEntity();
            result = EntityUtils.toString(entity1);
//            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("koudai crawler energy get fail");
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("koudai crawler close energy http response fail");
                }
            }
        }
        long getWebDataTime = System.currentTimeMillis();
        log.info("koudai crawler energy get time " + (getWebDataTime - startTime) + " ms");
        if ("".equals(result)) {
            log.info("koudai crawler no energy data got");
            return;
        }
        // 处理网页数据
        processWebData(result);
        long finishTime = System.currentTimeMillis();
        log.info("koudai crawler energy process time " + (finishTime - getWebDataTime) + " ms");
    }

    private void processWebData(String webData) {
        JSONObject jsonObject = JSONObject.parseObject(webData);
        if(!"0".equals(jsonObject.getString("TranErrorCode"))) {
            log.info("web energy data is not ok");
            return;
        }
        JSONArray dataArray = jsonObject.getJSONArray("market");
        if(dataArray == null || dataArray.isEmpty()) {
            log.info("web energy data array is not ready");
            return;
        }
        for(int i = 0; i < dataArray.size(); i++) {
            JSONObject data = dataArray.getJSONObject(i);
            String code = idCodeMap.get(data.getString("proID"));
            Float buy = Float.parseFloat(data.getString("buyRate"));
            Float sell = Float.parseFloat(data.getString("sellRate"));
            Date time = TypeUtils.castToDate(data.getString("quoteDate")
                    + " " + data.getString("quoteTime"));
//            System.out.println(code + " | " + buy + " | " + sell + " | " + time);
            // 更新行情
            globalMarket.updateMarket(code, buy, sell, time);
        }
    }

    public static void main(String[] args) throws Exception{
        CloseableHttpResponse response = null;
        String result = "";
        try {
            response = httpclient.execute(httpPost);
            HttpEntity entity1 = response.getEntity();
            result = EntityUtils.toString(entity1);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("crawler get web fail");
        } finally {
            response.close();
        }

    }

}
