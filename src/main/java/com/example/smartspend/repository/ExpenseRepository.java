package com.example.smartspend.repository;

import com.example.smartspend.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {
}
public interface ExpenseRepository extends MongoRepository<Expense, String> {

    List<Expense> findBySessionIdOrderByCreatedAtDesc(String sessionId);
}

