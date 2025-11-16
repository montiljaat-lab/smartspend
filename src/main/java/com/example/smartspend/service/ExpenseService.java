package com.example.smartspend.service;


import com.example.smartspend.model.Expense;
import com.example.smartspend.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository repo;

    public ExpenseService(ExpenseRepository repo) {
        this.repo = repo;
    }

    // Yeh method naam/params hum jane-bujhe same rakh rahe hain
    // taaki agar koi purana controller isko call kar raha ho to error na aaye
    public void addExpense(Long userId, String title, double amount, String category) {
        Expense expense = new Expense();
        expense.setTitle(title);
        expense.setCategory(category);
        expense.setAmount(amount);
        expense.setCreatedAt(LocalDateTime.now());
        // userId field hum ignore kar rahe hain (demo project)
        repo.save(expense);
    }

    // Purana "getExpensesForUser" bhi rakhenge, par simple bana ke
    public List<Expense> getExpensesForUser(Long userId) {
        // Abhi ke liye userId ignore, sirf sab expenses return kar rahe
        return repo.findAll();
    }

    // Purana delete method bhi simple bana diya
    public void deleteExpensesForUser(Long userId) {
        // User ke hisaab se delete nahi, sab delete (demo/cleanup)
        repo.deleteAll();
    }
}
