package com.example.smartspend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(
        origins = {
                "https://smartspend-flame.vercel.app",
                "http://localhost:8080"
        },
        maxAge = 3600
)
public class ExpenseController {

    // Simple in-memory storage (server restart hone par clear ho jayega)
    private final List<ExpenseDto> expenses = new CopyOnWriteArrayList<>();

    // GET  /api/expenses/list
    @GetMapping("/list")
    public List<ExpenseDto> listExpenses() {
        return new ArrayList<>(expenses);
    }

    // POST /api/expenses/add
    @PostMapping("/add")
    public ResponseEntity<?> addExpense(@RequestBody ExpenseDto body) {
        if (body == null || body.title == null || body.title.isBlank()) {
            return ResponseEntity.badRequest().body("Title required");
        }
        if (body.amount <= 0) {
            return ResponseEntity.badRequest().body("Amount must be > 0");
        }

        // default values so frontend kabhi null na dekhe
        if (body.category == null || body.category.isBlank()) {
            body.category = "Other";
        }
        if (body.method == null || body.method.isBlank()) {
            body.method = "N/A";
        }

        // createdAt set karo (dashboard isko date ke liye use karta hai)
        body.createdAt = Instant.now().toString();

        expenses.add(body);
        return ResponseEntity.ok("OK");
    }

    // Simple DTO class (entity / DB ki zaroorat nahi)
    public static class ExpenseDto {
        public String title;
        public String category;
        public String method;
        public double amount;
        public String date;      // optional, frontend se aayega (yyyy-MM-dd)
        public String createdAt; // server side set
    }
}
