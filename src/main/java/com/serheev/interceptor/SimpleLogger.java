package com.serheev.interceptor;

import org.apache.log4j.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class SimpleLogger {
    private static Logger log = Logger.getLogger(SimpleLogger.class);

    @AroundInvoke
    public Object addLog(InvocationContext context) throws Exception {
        log.info("Object: " + context.getTarget().getClass());
        log.info("Method: " + context.getMethod());
        return context.proceed();
    }
}
