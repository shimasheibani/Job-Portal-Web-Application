package com.shima.project.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Spring {
    private static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("spring.xml");

    public static Object getBean(String name) {

        return CONTEXT.getBean(name);
    }
}