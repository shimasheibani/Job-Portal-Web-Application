package com.project.spring.service;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("ps")
//@Scope("portotype")
public class PersonService implements IService {
//    @Autowired
    private String name;
    private String family;
    private CarService carService;

    public String getFamily() {
        return family;
    }

    public String getName() {
        return name;
    }

    public PersonService setFamily(String family) {
        this.family = family;
        return this;
    }

    public PersonService setName(String name) {
        this.name = name;
        return this;
    }

    public CarService getCarService() {
        return carService;
    }

    public PersonService setCarService(CarService carService) {
        this.carService = carService;
        return this;
    }
    @Override
    public void execute(String name) {
        System.out.println(name + "'s engine is working as hors");
    }

}
