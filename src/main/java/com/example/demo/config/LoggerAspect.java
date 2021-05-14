package com.example.demo.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Aspect
@Component
public class LoggerAspect {

    private static final Logger LOG = Logger.getLogger(LoggerAspect.class.getName());


    @Before("execution(* *(..)) && @annotation(com.example.demo.LogMethodParam)")

    public void logMethodParams(JoinPoint jp) throws Throwable {

        String[] argNames = ((MethodSignature) jp.getSignature()).getParameterNames();
        if (argNames == null || argNames.length == 0)
            return;

        Object[] values = jp.getArgs();

        LOG.log(Level.INFO,"*********************");
        LOG.log(Level.INFO,"signature:"+jp.getSignature());
        for (int i = 0; i < argNames.length; i++) {
            LOG.log(Level.INFO,argNames[i] + "--->" + values[i]);

        }
        LOG.log(Level.INFO,"*********************");


    }


}