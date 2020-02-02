package com.serheev.processor;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class LoggingBPP implements BeanPostProcessor {

    private static Logger log = Logger.getLogger("LogginingBPP.class");

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("BPP-Before for bean " + beanName + " is executing...");
        return bean;
    }
}
