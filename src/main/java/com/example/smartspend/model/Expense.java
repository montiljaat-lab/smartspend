package com.example.smartspend.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String title;

    private double amount;

    private String category;

    private Instant date;

    public Expense() {}

    public Expense(Long userId, String title, double amount, String category, Instant date) {
        this.userId = userId;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId(){ return userId; }
    public void setUserId(Long userId){ this.userId = userId; }

    public String getTitle(){ return title; }
    public void setTitle(String title){ this.title = title; }

    public double getAmount(){ return amount; }
    public void setAmount(double amount){ this.amount = amount; }

    public String getCategory(){ return category; }
    public void setCategory(String category){ this.category = category; }

    public Instant getDate(){ return date; }
    public void setDate(Instant date){ this.date = date; }
}