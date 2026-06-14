import payment.PaymentProcessor;
import payment.interfaces.PaymentNotifier;
import payment.interfaces.PaymentRepository;
import payment.interfaces.TransactionStore;
import payment.services.DatabaseRepository;
import payment.services.InMemoryTransactionStore;
import payment.services.MockEmailNotifier;
import payment.ui.PaymentMenu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
        System.out.println("\n" +
        "╔═══════════════════════════════════════╗\n" +
        "║     PAYMENT GATEWAY REFACTORED        ║\n" +
        "║   Sistem Pembayaran Modern & Dinamis  ║\n" +
        "╚═══════════════════════════════════════╝");

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
        TransactionStore store = new InMemoryTransactionStore();

        // Injeksi dependencies (DIP) ke PaymentProcessor (High-level module)
        PaymentProcessor processor = new PaymentProcessor(repo, notifier, store);

        // Mode Interaktif - Menu dinamis
        Scanner scanner = new Scanner(System.in);
        PaymentMenu menu = new PaymentMenu(processor, store, scanner);
        menu.start();

        scanner.close();
        System.out.println("\n=== Program Selesai ===");
    }
}
