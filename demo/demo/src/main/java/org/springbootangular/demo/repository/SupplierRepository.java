package org.springbootangular.demo.repository;

import org.springbootangular.demo.entity.Supplier;
import org.springbootangular.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
