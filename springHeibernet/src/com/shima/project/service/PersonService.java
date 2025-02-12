package com.shima.project.service;

import com.shima.project.entity.Person;
import com.shima.project.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PersonService {
    @Autowired
    private static final PersonRepository personRepository = new PersonRepository();
    public void save(Person p){
        personRepository.save(p);
    }
    public void update(Person p){
        personRepository.update(p);
    }
    public void remove(Person p){
        personRepository.delete(p);
    }

    public List<Person> findAll(){
        return personRepository.findAll(Person.class);

    }

}
