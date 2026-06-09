package payment.services;

import payment.interfaces.PaymentRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseRepository implements PaymentRepository {
    
    // Encapsulation: Menggunakan access modifier private
    private String dbUrl;
    private String user;
    private String password;

    // Dependency Injection melalui konstruktor
    public DatabaseRepository(String dbUrl, String user, String password) {
        this.dbUrl = dbUrl;
        this.user = user;
        this.password = password;
        
        // Memastikan tabel tersedia saat repository diinisialisasi
        initializeDatabase();
    }

    // Method private untuk auto-migrate tabel
    private void initializeDatabase() {
        // Menyesuaikan dengan tabel 'transactions' dari kerangka Anda
        String createTableQuery = "CREATE TABLE IF NOT EXISTS transactions ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "amount DOUBLE NOT NULL, "
                + "method VARCHAR(50) NOT NULL, "
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

        // Menggunakan try-with-resources untuk mencegah kebocoran memori [cite: 79]
        try (Connection conn = DriverManager.getConnection(dbUrl, user, password);
             PreparedStatement pstmt = conn.prepareStatement(createTableQuery)) {

            pstmt.execute();
            System.out.println("[DB] Tabel transactions siap digunakan.");

        } catch (SQLException e) {
            System.err.println("[DB ERROR] Gagal membuat tabel: " + e.getMessage());
        }
    }

    @Override
    public void saveTransaction(double amount, String methodType) {
        String query = "INSERT INTO transactions (amount, method) VALUES (?, ?)";

        // Menggunakan try-with-resources untuk mencegah kebocoran memori [cite: 79]
        try (Connection conn = DriverManager.getConnection(dbUrl, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setDouble(1, amount);
            pstmt.setString(2, methodType);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("[DB] Berhasil menyimpan log transaksi (" + methodType + ") ke MySQL.");
            }

        } catch (SQLException e) {
            System.err.println("[DB ERROR] Gagal menyimpan data transaksi: " + e.getMessage());
        }
    }
}