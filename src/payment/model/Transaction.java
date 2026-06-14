package payment.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private static int counter = 0;
    private int id;
    private double amount;
    private String methodName;
    private String customerContact;
    private LocalDateTime timestamp;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public Transaction(double amount, String methodName, String customerContact) {
        this.id = ++counter;
        this.amount = amount;
        this.methodName = methodName;
        this.customerContact = customerContact;
        this.timestamp = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getFormattedTimestamp() {
        return timestamp.format(FORMATTER);
    }

    public static void resetCounter() {
        counter = 0;
    }

    @Override
    public String toString() {
        return String.format("| %-3d | %-12s | Rp %-12.2f | %-25s | %-25s |",
                id, methodName, amount, customerContact, getFormattedTimestamp());
    }
}
