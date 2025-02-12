package com.project.spring.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    public void start(){
        System.out.println("Engine is started...");
    }
}
