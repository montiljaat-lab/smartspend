package com.example.smartspend.controller;

import com.example.smartspend.model.Expense;
import com.example.smartspend.repository.ExpenseRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/public/expenses") // <- naya path, token check se bahar
@CrossOrigin


public class ExpenseController {

    private final ExpenseRepository repo;

    public ExpenseController(ExpenseRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/add")
    public Expense addExpense(@RequestBody Expense req) {
        req.setCreatedAt(LocalDateTime.now());
        return repo.save(req);
    }

    @GetMapping("/list")
    public List<Expense> list() {
        return repo.findAll();
    }
}
