package com.example.smartspend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    // 🔹 In-memory list storing ALL users’ expenses
    // Now we will store sessionId also → so every user ka alag hoga
    private final List<ExpenseDto> expenses = new CopyOnWriteArrayList<>();


    // -------------------------------------------------------------------------
    // 🔹 GET: /api/expenses/list?sessionId=xxxx
    //     → returns ONLY that user’s expenses
    // -------------------------------------------------------------------------
    @GetMapping("/list")
    public List<ExpenseDto> listExpenses(@RequestParam String sessionId) {

        List<ExpenseDto> result = new ArrayList<>();

        for (ExpenseDto e : expenses) {
            if (e.sessionId != null && e.sessionId.equals(sessionId)) {
                result.add(e);
            }
        }

        return result;
    }


    // -------------------------------------------------------------------------
    // 🔹 POST: /api/expenses/add
    //     → add new expense with sessionId
    // -------------------------------------------------------------------------
    @PostMapping("/add")
    public ResponseEntity<?> addExpense(@RequestBody ExpenseDto body) {

        if (body == null || body.title == null || body.title.isBlank()) {
            return ResponseEntity.badRequest().body("Title required");
        }
        if (body.amount <= 0) {
            return ResponseEntity.badRequest().body("Amount must be > 0");
        }
        if (body.sessionId == null || body.sessionId.isBlank()) {
            return ResponseEntity.badRequest().body("sessionId required");
        }

        // Default values
        if (body.category == null || body.category.isBlank()) {
            body.category = "Other";
        }
        if (body.method == null || body.method.isBlank()) {
            body.method = "N/A";
        }

        // Timestamp
        body.createdAt = Instant.now().toString();

        expenses.add(body);
        return ResponseEntity.ok("OK");
    }


    // -------------------------------------------------------------------------
    // 🔹 DTO
    // -------------------------------------------------------------------------
    public static class ExpenseDto {
        public String sessionId; // 🔥 Now added field
        public String title;
        public String category;
        public String method;
        public double amount;
        public String date;      // optional
        public String createdAt; // auto set
    }
}
