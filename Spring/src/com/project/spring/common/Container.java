package com.project.spring.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Container {
    private Container() {
    }

    private static final ApplicationContext CONTAINER = new ClassPathXmlApplicationContext("iocDemo.xml");

    public static Object getBean(String beanName) {
        return CONTAINER.getBean(beanName);
    }
}
