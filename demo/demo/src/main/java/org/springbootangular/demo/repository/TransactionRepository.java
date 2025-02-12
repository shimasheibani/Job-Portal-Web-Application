package org.springbootangular.demo.repository;

import org.springbootangular.demo.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT o FROM TransactionDTO o " + " where year(o.createAt) = :year AND MONTH(o.createAt) = :month ")
    List<Transaction> findAllByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT o FROM TransactionDTO o" +
            " LEFT JOIN o.product p" +
            " WHERE (:searchText IS NULL OR " +
            " LOWER(o.description) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            " LOWER(o.note) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            " LOWER(o.transactionStatus) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            " LOWER(p.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            " LOWER(p.sku) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<Transaction> searchTransactions(@Param("searchText") String searchText, Pageable pageable);
}
