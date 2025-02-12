package com.shima.project.service;

import com.shima.project.entity.Car;
import com.shima.project.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CarService {
    @Autowired
    private static final PersonRepository personRepository = new PersonRepository();
    public void save(Car c){
        personRepository.save(c);
    }
    public void update(Car c){
        personRepository.update(c);
    }
    public void remove(Car c){
        personRepository.delete(c);
    }

    public List<Car> findAll(){
        return personRepository.findAll(Car.class);

    }

}
