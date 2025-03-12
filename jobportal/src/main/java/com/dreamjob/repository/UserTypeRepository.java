package com.dreamjob.repository;

import com.dreamjob.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
}
