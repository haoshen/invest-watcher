package com.haoshen.money.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.haoshen.money.crawler.CrawlerScheduler;
import com.haoshen.money.utils.ConstUtil;

@Service("crawlerManager")
public class CrawlerManager {

    @Resource
    private CrawlerScheduler crawlerScheduler;

    public boolean pauseCrawler(String root, String crawlerName) {
        if (!ConstUtil.checkRoot(root)) {
            return false;
        }
        return crawlerScheduler.pauseCrawler(crawlerName);
    }

    public boolean resumeCrawler(String root, String crawlerName) {
        if (!ConstUtil.checkRoot(root)) {
            return false;
        }
        return crawlerScheduler.resumeCrawler(crawlerName);
    }

    public boolean triggerCrawler(String root, String crawlerName) {
        if (!ConstUtil.checkRoot(root)) {
            return false;
        }
        return crawlerScheduler.triggerCrawler(crawlerName);
    }

    public boolean rescheduleCrawler(String root, String crawlerName,
                                     String newCrawlerName, String newExpression) {
        if (!ConstUtil.checkRoot(root)) {
            return false;
        }
        return crawlerScheduler.rescheduleCrawler(crawlerName, newCrawlerName, newExpression);
    }





}
