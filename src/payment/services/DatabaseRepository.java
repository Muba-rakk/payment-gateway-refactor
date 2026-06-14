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
    private boolean isConnected;

    public DatabaseRepository(String dbUrl, String user, String password) {
        this.dbUrl = dbUrl;
        this.user = user;
        this.password = password;
        this.isConnected = testConnection();
        
        if (isConnected) {
            initializeDatabase();
        } else {
            System.out.println("[DB INFO] Mode fallback aktif. Transaksi akan disimpan in-memory saja.");
        }
    }

    private boolean testConnection() {
        try {
            Connection conn = DriverManager.getConnection(dbUrl, user, password);
            boolean valid = conn.isValid(5);
            conn.close();
            return valid;
        } catch (SQLException e) {
            System.out.println("[DB WARNING] Tidak dapat terhubung ke MySQL: " + e.getMessage());
            return false;
        }
    }

    private void initializeDatabase() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS transactions ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "amount DOUBLE NOT NULL, "
                + "method VARCHAR(50) NOT NULL, "
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

        try (Connection conn = DriverManager.getConnection(dbUrl, user, password);
             PreparedStatement pstmt = conn.prepareStatement(createTableQuery)) {

            pstmt.execute();
            System.out.println("[DB] Tabel transactions siap digunakan.");

        } catch (SQLException e) {
            System.err.println("[DB ERROR] Gagal membuat tabel: " + e.getMessage());
            isConnected = false;
        }
    }

    @Override
    public void saveTransaction(double amount, String methodType) {
        if (!isConnected) {
            System.out.println("[DB FALLBACK] Transaksi disimpan in-memory (DB tidak tersedia).");
            return;
        }

        String query = "INSERT INTO transactions (amount, method) VALUES (?, ?)";

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
