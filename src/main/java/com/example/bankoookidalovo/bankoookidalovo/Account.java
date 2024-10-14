package com.example.bankoookidalovo.bankoookidalovo;

import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {
    private int id;
    private String name;
    private double balance;
    private double annualInterestRate;
    private Date dateCreated;
    private List<String> transactions = new ArrayList<>();

    public Account() {
        this.dateCreated = new Date();
    }

    public Account(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.dateCreated = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public double getMonthlyInterest() {
        return (balance * (annualInterestRate / 12 / 100));
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactions.add("Снято: " + amount + " | Баланс: " + balance);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Недостаточно средств");
            alert.setHeaderText("Недостаточно средств");
            alert.setContentText("Ваш баланс: " + balance);
            alert.showAndWait();
        }
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add("Внесено: " + amount + " | Баланс: " + balance);
    }

    public List<String> getTransactions() {
        return transactions;
    }
}



