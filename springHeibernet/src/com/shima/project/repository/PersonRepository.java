package com.shima.project.repository;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;


@Repository
@Scope("prototype")
public class PersonRepository<T> {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void save(T t) {
       entityManager.persist(t);
    }
    public void update(T t){
        entityManager.merge(t);
    }
    public void delete(T t){
        entityManager.remove(entityManager.merge(t));
    }
    public List<T> findAll(Class<T> t){
        Entity entity = t.getAnnotation(Entity.class);
        String entityName =entity.name();
        Query  query = entityManager.createQuery("select o from "+entityName+" o");
        return query.getResultList();
    }

}
