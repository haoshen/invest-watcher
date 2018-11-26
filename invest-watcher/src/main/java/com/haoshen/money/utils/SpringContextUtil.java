package com.haoshen.money.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("springContextUtil")
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    //通过class获取Bean.
    public static<T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
//        return null;
    }

    //通过name,以及Clazz返回指定的Bean
    public static<T> T getBean(String name,Class<T> clazz){
        return applicationContext.getBean(name, clazz);
    }
}
