import payment.PaymentProcessor;
import payment.interfaces.PaymentMethod;
import payment.interfaces.PaymentNotifier;
import payment.interfaces.PaymentRepository;
import payment.methods.CreditCardPayment;
import payment.methods.PayPalPayment;
import payment.methods.GoPayPayment;
import payment.services.DatabaseRepository;
import payment.services.MockEmailNotifier;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Payment Gateway Refactored ===");

        // Mengambil kredensial dari environment variable
        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPass = System.getenv("DB_PASSWORD");
        String smtp = System.getenv("SMTP_SERVER");

        // Validasi jika kredensial belum di-set
        if (dbUrl == null || dbUser == null || smtp == null) {
            System.err.println("[ERROR] Kredensial environment belum di-set.");
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