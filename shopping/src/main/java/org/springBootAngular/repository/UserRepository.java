package org.springBootAngular.repository;

import org.springBootAngular.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

//    List<User> findAll(Sort.Direction direction, String id);
}
