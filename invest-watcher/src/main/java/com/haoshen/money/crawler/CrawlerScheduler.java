package com.haoshen.money.crawler;

import java.util.HashSet;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.haoshen.money.crawler.koudai.KouDaiEnergyCrawler;
import com.haoshen.money.crawler.koudai.KouDaiMetalCrawler;

@Service("crawlerScheduler")
@DependsOn("springContextUtil")
public class CrawlerScheduler implements InitializingBean {

    private static Logger log = LoggerFactory.getLogger(CrawlerScheduler.class);

    private static String cronKoudaiExperssion = "*/1 * * * * ?";

    private Scheduler scheduler;

    private Set<String> crawlerSet;

    @Override
    public void afterPropertiesSet() {
        init();
    }

    public void init() {
        crawlerSet = new HashSet<>();
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            addCrawlers();
            scheduler.start();
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("Initial crawlers fail");
        }
        log.warn("Initial crawlers success");
    }

    public boolean pauseCrawler(String crawlerName) {
        if (crawlerName == null || !crawlerSet.contains(crawlerName)) {
            return false;
        }
        try {
            scheduler.pauseJob(JobKey.jobKey(getJobName(crawlerName), getGroupName(crawlerName)));
        } catch (Exception e) {
            log.error("pause crawler " + crawlerName + " fail");
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean resumeCrawler(String crawlerName) {
        if (crawlerName == null || !crawlerSet.contains(crawlerName)) {
            return false;
        }
        try {
            scheduler.resumeJob(JobKey.jobKey(getJobName(crawlerName), getGroupName(crawlerName)));
        } catch (Exception e) {
            log.error("resume crawler " + crawlerName + " fail");
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean triggerCrawler(String crawlerName) {
        if (crawlerName == null || !crawlerSet.contains(crawlerName)) {
            return false;
        }
        try {
            scheduler.triggerJob(JobKey.jobKey(getJobName(crawlerName), getGroupName(crawlerName)));
        } catch (Exception e) {
            log.error("trigger crawler " + crawlerName + " fail");
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean rescheduleCrawler(String crawlerName, String newCrawlerName, String newExpression) {
        if (crawlerName == null || crawlerName.equals(newCrawlerName) || !crawlerSet.contains(crawlerName)) {
            return false;
        }
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(getTriggerName(crawlerName), getGroupName(crawlerName));
            Trigger newTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(getTriggerName(newCrawlerName), getGroupName(newCrawlerName))
                    .withSchedule(CronScheduleBuilder.cronSchedule(newExpression)).build();
            scheduler.rescheduleJob(triggerKey, newTrigger);
        } catch (Exception e) {
            log.error("reschedule crawler " + crawlerName + " fail");
            log.error(e.getMessage());
            return false;
        }
        crawlerSet.remove(crawlerName);
        crawlerSet.add(newCrawlerName);
        return true;
    }


    //初始化爬虫
    private void addCrawlers() throws Exception{
        addCrawler("kouda_metal", KouDaiMetalCrawler.class, cronKoudaiExperssion);
        addCrawler("koudai_energy", KouDaiEnergyCrawler.class, cronKoudaiExperssion);
    }

    private void addCrawler(String crawlerName, Class crawlerClass, String expr) throws Exception{
        //定义爬虫的job和trigger
        JobDetail jobDetail = JobBuilder.newJob(crawlerClass)
                .withIdentity(getJobName(crawlerName), getGroupName(crawlerName)).build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(getTriggerName(crawlerName), getGroupName(crawlerName))
                .withSchedule(CronScheduleBuilder.cronSchedule(expr)).build();
        // 添加定时爬取任务
        scheduler.scheduleJob(jobDetail, trigger);
        crawlerSet.add(crawlerName);
    }

    private String getJobName(String crawlerName) {
        return crawlerName + "_job";
    }

    private String getTriggerName(String crawlerName) {
        return crawlerName + "_trigger";
    }

    private String getGroupName(String crawlerName) {
//        return crawlerName + "Group";
        return "invest_watcher_group";
    }

}
