package com.example.bankoookidalovo.bankoookidalovo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

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

    @FXML
    private TextField nameField;

    @FXML
    private Button createUserButton;

    private Account account = new Account();

    @FXML
    private void handleDeposit() {
        double amount = Double.parseDouble(amountField.getText());
        int id = Integer.parseInt(idField.getText());

        try (Connection conn = DatabaseConnection.connect()) {
            String updateBalanceSql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateBalanceSql)) {
                updateStmt.setDouble(1, amount);
                updateStmt.setInt(2, id);
                updateStmt.executeUpdate();
            }

            String insertTransactionSql = "INSERT INTO transactions (account_id, transaction_detail) VALUES (?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertTransactionSql)) {
                insertStmt.setInt(1, id);
                insertStmt.setString(2, "Внесено: " + amount);
                insertStmt.executeUpdate();
            }

            String checkBalanceSql = "SELECT balance FROM accounts WHERE id = ?";
            try (PreparedStatement balanceStmt = conn.prepareStatement(checkBalanceSql)) {
                balanceStmt.setInt(1, id);
                ResultSet rs = balanceStmt.executeQuery();
                if (rs.next()) {
                    double newBalance = rs.getDouble("balance");
                    balanceField.setText(String.valueOf(newBalance));
                    resultLabel.setText("Баланс обновлен: " + newBalance);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resultLabel.setText("Ошибка при внесении депозита");
        }
    }


    @FXML
    private void handleWithdraw() {
        double amount = Double.parseDouble(amountField.getText());
        int id = Integer.parseInt(idField.getText());

        try (Connection conn = DatabaseConnection.connect()) {
            String checkBalanceSql = "SELECT balance FROM accounts WHERE id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkBalanceSql)) {
                checkStmt.setInt(1, id);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    double currentBalance = rs.getDouble("balance");
                    if (amount > currentBalance) {
                        resultLabel.setText("Недостаточно средств для вывода.");
                        return;
                    }
                }
            }

            String updateBalanceSql = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateBalanceSql)) {
                updateStmt.setDouble(1, amount);
                updateStmt.setInt(2, id);
                updateStmt.executeUpdate();
            }

            String insertTransactionSql = "INSERT INTO transactions (account_id, transaction_detail) VALUES (?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertTransactionSql)) {
                insertStmt.setInt(1, id);
                insertStmt.setString(2, "Снято: " + amount);
                insertStmt.executeUpdate();
            }

            try (PreparedStatement checkStmt = conn.prepareStatement(checkBalanceSql)) {
                checkStmt.setInt(1, id);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    double newBalance = rs.getDouble("balance");
                    balanceField.setText(String.valueOf(newBalance));
                    resultLabel.setText("Баланс обновлен: " + newBalance);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resultLabel.setText("Ошибка при выводе средств");
        }
    }


    @FXML
    private void handleCalculateInterest() {
        int id = Integer.parseInt(idField.getText());
        try (Connection conn = DatabaseConnection.connect()) {
            String checkBalanceSql = "SELECT balance FROM accounts WHERE id = ?";
            double currentBalance = 0;
            try (PreparedStatement checkStmt = conn.prepareStatement(checkBalanceSql)) {
                checkStmt.setInt(1, id);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    currentBalance = rs.getDouble("balance");
                }
            }
            String checkInterestSql = "SELECT annual_interest_rate FROM accounts WHERE id = ?";
            double currentInterest = 0;
            try (PreparedStatement checkStmt = conn.prepareStatement(checkInterestSql)){
                checkStmt.setInt(1, id);
                ResultSet rs = checkStmt.executeQuery();
                if(rs.next()){
                    currentInterest = rs.getDouble("annual_interest_rate");
                }
            }
            double everyMonthProcent = currentBalance * (currentInterest / 100) / 12;
            String interestOutput = String.format("%.2f", everyMonthProcent);
            resultLabel.setText("Ежемесячные проценты: " + interestOutput);
            balanceField.setText(String.valueOf(currentBalance));
            interestField.setText(String.valueOf(currentInterest));

        } catch (SQLException e) {
            e.printStackTrace();
            resultLabel.setText("Ошибка при выводе средств");
        }

    }

    @FXML
    private void handleCreateUser() {
        String name = nameField.getText();
        int id = Integer.parseInt(idField.getText());

        try (Connection conn = DatabaseConnection.connect()) {
            String sql = "INSERT INTO accounts (id, name, balance, annual_interest_rate) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.setString(2, name);
                stmt.executeUpdate();
                resultLabel.setText("Пользователь создан: " + name + " (ID: " + id + ")");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultLabel.setText("Ошибка при создании пользователя");
        }
    }

    @FXML
    private void handleCheckBalance() {
        int id = Integer.parseInt(idField.getText());

        try (Connection conn = DatabaseConnection.connect()) {
            String sql = "SELECT balance FROM accounts WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    double balance = rs.getDouble("balance");
                    balanceField.setText(String.valueOf(balance));
                    resultLabel.setText("Баланс пользователя (ID: " + id + "): " + balance);
                } else {
                    resultLabel.setText("Пользователь не найден");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultLabel.setText("Ошибка при проверке баланса");
        }
    }

    @FXML
    private void handleViewTransactions() {
        int id = Integer.parseInt(idField.getText());

        try (Connection conn = DatabaseConnection.connect()) {
            String sql = "SELECT transaction_detail FROM transactions WHERE account_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                StringBuilder transactions = new StringBuilder("История операций:\n");
                while (rs.next()) {
                    transactions.append(rs.getString("transaction_detail")).append("\n");
                }
                resultLabel.setText(transactions.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultLabel.setText("Ошибка при просмотре истории операций");
        }
    }
}
