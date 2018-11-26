package com.haoshen.money.utils;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("constUtil")
public class ConstUtil implements InitializingBean {

    private static Logger log = LoggerFactory.getLogger(ConstUtil.class);

    public static String ROOT_PASSWORD;

    @Override
    public void afterPropertiesSet() throws Exception {
        //读取配置文件
        Properties properties = new Properties();
        properties.load(ConstUtil.class.getClassLoader().getResourceAsStream("project.properties"));
        ROOT_PASSWORD = properties.getProperty("root.password");
        log.warn("ROOT_PASSWORD = " + ROOT_PASSWORD);
    }

    public static boolean checkRoot(String password) {
        return ROOT_PASSWORD.equals(password);
    }
}
