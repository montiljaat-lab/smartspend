package com.example.smartspend.service;

import com.example.smartspend.model.Expense;
import com.example.smartspend.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository repo;

    public ExpenseService(ExpenseRepository repo) { this.repo = repo; }

    public Expense addExpense(Long userId, String title, double amount, String category) {
        Expense e = new Expense(userId, title, amount, category, Instant.now());
        return repo.save(e);
    }

    public List<Expense> listForUser(Long userId) {
        return repo.findByUserIdOrderByDateDesc(userId);
    }

    public Optional<Expense> findById(Long id) {
        return repo.findById(id);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}