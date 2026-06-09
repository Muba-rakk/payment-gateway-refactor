import payment.PaymentProcessor;
import payment.interfaces.PaymentMethod;
import payment.interfaces.PaymentNotifier;
import payment.interfaces.PaymentRepository;
import payment.methods.CreditCardPayment;
import payment.methods.PayPalPayment;
import payment.methods.GoPayPayment;
import payment.services.DatabaseRepository;
import payment.services.MockEmailNotifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    
    // Helper method untuk membaca file .env secara manual
    private static Map<String, String> loadEnvFile(String filePath) {
        Map<String, String> envMap = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                if (line.trim().isEmpty() || line.trim().startsWith("#")) continue;
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    envMap.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            // Abaikan jika file tidak ditemukan
        }
        return envMap;
    }

    public static void main(String[] args) {
        System.out.println("=== Payment Gateway Refactored ===");

        // Load kredensial dari file .env.local sebagai fallback
        Map<String, String> localEnv = loadEnvFile(".env.local");

        // Prioritaskan System.getenv(), jika kosong ambil dari file .env.local
        String dbUrl = System.getenv("DB_URL") != null ? System.getenv("DB_URL") : localEnv.get("DB_URL");
        String dbUser = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : localEnv.get("DB_USER");
        String dbPass = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : localEnv.get("DB_PASSWORD");
        String smtp = System.getenv("SMTP_SERVER") != null ? System.getenv("SMTP_SERVER") : localEnv.get("SMTP_SERVER");

        // Validasi jika kredensial masih belum di-set
        if (dbUrl == null || dbUser == null || smtp == null) {
            System.err.println("[ERROR] Kredensial environment belum di-set.");
            System.err.println("Pastikan file .env.local sudah dibuat dan diisi dengan benar.");
            return;
        }

        // Buat instance dari layer Data/Services (Low-level module)
        PaymentRepository repo = new DatabaseRepository(dbUrl, dbUser, dbPass);
        PaymentNotifier notifier = new MockEmailNotifier(smtp);

        // Injeksi dependencies (DIP) ke PaymentProcessor (High-level module)
        PaymentProcessor processor = new PaymentProcessor(repo, notifier);

        System.out.println("\n--- Transaksi 1 (Legacy): Credit Card ---");
        PaymentMethod cc = new CreditCardPayment("1234-5678-9012-3456", "123", "12/28");
        processor.processPayment(cc, 150000, "customer1@example.com");

        System.out.println("\n--- Transaksi 2 (Legacy): PayPal ---");
        PaymentMethod paypal = new PayPalPayment("user@paypal.com");
        processor.processPayment(paypal, 250000, "user@paypal.com");

        System.out.println("\n--- Transaksi 3 (Fitur Baru): GoPay ---");
        PaymentMethod gopay = new GoPayPayment("081234567890");
        processor.processPayment(gopay, 50000, "user@gopay.com");
        
        System.out.println("\n=== Semua Transaksi Selesai ===");
    }
}