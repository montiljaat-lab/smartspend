package com.example.smartspend.controller;

import com.example.smartspend.model.Expense;
import com.example.smartspend.service.ExpenseService;
import com.example.smartspend.config.AuthPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ExpenseController {
    private final ExpenseService service;
    public ExpenseController(ExpenseService service){ this.service = service; }

    public static class ExpenseRequest {
        public String title;
        public Double amount;
        public String category;
    }

    // get all expenses for current user
    @GetMapping("/expenses")
    public ResponseEntity<?> list(HttpServletRequest req) {
        Object ap = req.getAttribute("authPrincipal");
        Long uid = null;
        if (ap instanceof AuthPrincipal) uid = ((AuthPrincipal) ap).getUserId();
        if (uid == null) return ResponseEntity.status(401).body(Map.of("message","Unauthorized"));
        List<Expense> list = service.listForUser(uid);
        return ResponseEntity.ok(list);
    }

    // add expense for current user
    @PostMapping("/expenses")
    public ResponseEntity<?> add(HttpServletRequest req, @RequestBody ExpenseRequest body) {
        Object ap = req.getAttribute("authPrincipal");
        Long uid = null;
        if (ap instanceof AuthPrincipal) uid = ((AuthPrincipal) ap).getUserId();
        if (uid == null) return ResponseEntity.status(401).body(Map.of("message","Unauthorized"));
        if (body == null || body.title == null || body.amount == null) {
            return ResponseEntity.badRequest().body(Map.of("message","Invalid request"));
        }
        Expense e = service.addExpense(uid, body.title, body.amount, body.category == null ? "Other" : body.category);
        return ResponseEntity.ok(e);
    }

    // delete expense (only owner)
    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<?> delete(HttpServletRequest req, @PathVariable Long id) {
        Object ap = req.getAttribute("authPrincipal");
        Long uid = null;
        if (ap instanceof AuthPrincipal) uid = ((AuthPrincipal) ap).getUserId();
        if (uid == null) return ResponseEntity.status(401).body(Map.of("message","Unauthorized"));
        var oe = service.findById(id);
        if (oe.isEmpty()) return ResponseEntity.notFound().build();
        Expense e = oe.get();
        if (!uid.equals(e.getUserId())) return ResponseEntity.status(403).body(Map.of("message","Forbidden"));
        service.delete(id);
        return ResponseEntity.ok(Map.of("message","Deleted"));
    }
}