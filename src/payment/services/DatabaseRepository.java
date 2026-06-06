package payment.services;

import payment.interfaces.PaymentRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseRepository implements PaymentRepository {
    private String dbUrl;
    private String user;
    private String password;

    public DatabaseRepository(String dbUrl, String user, String password) {
        this.dbUrl = dbUrl;
        this.user = user;
        this.password = password;
    }

    @Override
    public void saveTransaction(double amount, String methodType) {
        // TODO: Implementasikan koneksi JDBC dan query INSERT di sini.
        // Tabel: transactions (amount DOUBLE, method VARCHAR)
        // Ingat untuk menggunakan try-with-resources untuk Connection dan PreparedStatement.
        
        System.out.println("[TODO] saveTransaction belum diimplementasikan untuk: " + methodType);
    }
}