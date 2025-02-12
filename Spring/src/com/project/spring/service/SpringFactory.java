//package com.project.spring.service;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SpringFactory {
//    @Bean(name = "getPersonService2")
//    @Scope("prototype")
//    public PersonService getUserFromGoogleAPI(){
//        System.out.println("getPersonService2");
//        PersonService personService = new PersonService();
//        return personService;
//    }
//
////    @Bean(name = "ServiceImplement1")
////    public ServiceImplement getServiceImplement(){
////        System.out.println("getServiceImplement");
////        //Rest->Google->JSON->SHima Sheibani
////        ServiceImplement serviceImplement = Naming.lookup();
////        return serviceImplement;
////    }
//
//}
