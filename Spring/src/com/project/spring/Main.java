package com.project.spring;

import com.project.spring.common.Container;
import com.project.spring.service.IService;
import com.project.spring.service.PersonService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//    ApplicationContext iocContainer = new ClassPathXmlApplicationContext("iocDemo.xml");
//    ServiceImplement service = (ServiceImplement) iocContainer.getBean("si");
//    service.execute();
        IService personService = (IService) Container.getBean("ps");
        personService.execute("BMW");
        personService.execute("Ford");

    }
}