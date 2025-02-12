package com.project.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class Rater {
    @Around("execution(* com.project.spring.service.*.*(..))")
    public void rating(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        System.out.println("before start pointcut");
        if(joinPoint.getArgs()[0].equals("BMW"))
            System.out.println("Engin is stop working.");
        else joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        System.out.println("after end pointcut");
        System.out.println("start at " + new Date(startTime) + " end at " + new Date(endTime) + " " + joinPoint.getSignature().getName() + " duration " + (endTime - startTime));

    }
}
