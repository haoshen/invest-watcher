package com.haoshen.money.controler;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haoshen.money.dto.ResultMessageDto;
import com.haoshen.money.manager.CrawlerManager;

@CrossOrigin
@RestController
@RequestMapping("/crawler")
public class CrawlerController {

    @Resource
    private CrawlerManager crawlerManager;

    @RequestMapping(value="/pause")
    public ResultMessageDto<Boolean> pauseCrawler(@RequestParam String root,
                                                  @RequestParam String name) {
        ResultMessageDto<Boolean> result = new ResultMessageDto();
        result.setResult(crawlerManager.pauseCrawler(root, name));
        return result;
    }

    @RequestMapping(value="/resume")
    public ResultMessageDto<Boolean> resumeCrawler(@RequestParam String root,
                                                   @RequestParam String name) {
        ResultMessageDto<Boolean> result = new ResultMessageDto();
        result.setResult(crawlerManager.resumeCrawler(root, name));
        return result;
    }

    @RequestMapping(value="/trigger")
    public ResultMessageDto<Boolean> triggerCrawler(@RequestParam String root,
                                                    @RequestParam String name) {
        ResultMessageDto<Boolean> result = new ResultMessageDto();
        result.setResult(crawlerManager.triggerCrawler(root, name));
        return result;
    }

    @RequestMapping(value="/reschedule")
    public ResultMessageDto<Boolean> rescheduleCrawler(@RequestParam String root,
                                                       @RequestParam String name,
                                                       @RequestParam String newName,
                                                       @RequestParam String expr) {
        ResultMessageDto<Boolean> result = new ResultMessageDto();
        result.setResult(crawlerManager.rescheduleCrawler(root, name, newName, expr));
        return result;
    }
}
