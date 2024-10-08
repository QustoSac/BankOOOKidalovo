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

    private Account account = new Account(1233, "Семен", 1100.0);

    @FXML
    private void handleDeposit() {
        double amount = Double.parseDouble(amountField.getText());
        account.deposit(amount);
        balanceField.setText(String.valueOf(account.getBalance()));
        resultLabel.setText("Транзакции: \n" + String.join("\n", account.getTransactions()));
    }

    @FXML
    private void handleWithdraw() {
        double amount = Double.parseDouble(amountField.getText());
        account.withdraw(amount);
        balanceField.setText(String.valueOf(account.getBalance()));
        resultLabel.setText("Транзакции: \n" + String.join("\n", account.getTransactions()));
    }

    @FXML
    private void handleCalculateInterest() {
        double interest = account.getMonthlyInterest();
        String interestOutput = String.format("%.2f", interest);
        resultLabel.setText("Ежемесячные проценты: " + interestOutput);
        balanceField.setText(String.valueOf(account.getBalance()));
        interestField.setText(String.valueOf(account.getAnnualInterestRate()));
    }
}
