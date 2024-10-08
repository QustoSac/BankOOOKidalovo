package com.example.bankoookidalovo.bankoookidalovo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BankAccountController {

    @FXML
    private TextField idField;

    @FXML
    private TextField balanceField;

    @FXML
    private TextField interestField;

    @FXML
    private TextField amountField;

    @FXML
    private Label resultLabel;

    private Account account = new Account(1, 0.0);

    @FXML
    private void handleDeposit() {
        double amount = Double.parseDouble(amountField.getText());
        account.deposit(amount);
        balanceField.setText(String.valueOf(account.getBalance()));
    }

    @FXML
    private void handleWithdraw() {
        double amount = Double.parseDouble(amountField.getText());
        account.withdraw(amount);
        balanceField.setText(String.valueOf(account.getBalance()));
    }

    @FXML
    private void handleCalculateInterest() {
        double interestRate = Double.parseDouble(interestField.getText());
        account.setAnnualInterestRate(interestRate);
        double interest = account.getMonthlyInterest();
        resultLabel.setText("Monthly Interest: " + interest);
    }
}
