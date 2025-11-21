package com.example.smartspend.service;

import com.example.smartspend.model.Expense;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ExpenseService {

    // 🔹 Simple in-memory list (ye sirf backup / old code ke liye hai)
    private final List<Expense> expenses = new CopyOnWriteArrayList<>();

    // Yeh method naam/params hum same rakh rahe hain
    // taaki agar koi purana controller isko call kar raha ho to error na aaye
    public void addExpense(Long userId, String title, double amount, String category) {
        Expense expense = new Expense();
        expense.setTitle(title);
        expense.setCategory(category);
        expense.setAmount(amount);
        expense.setCreatedAt(LocalDateTime.now());
        // userId ignore kar rahe hain (demo ke liye)
        expenses.add(expense);
    }

    // Purana "getExpensesForUser" — ab sabhi stored expenses return karega
    public List<Expense> getExpensesForUser(Long userId) {
        return new ArrayList<>(expenses);
    }

    // Purana delete method — sab expenses clear kar dega
    public void deleteExpensesForUser(Long userId) {
        expenses.clear();
    }
}
