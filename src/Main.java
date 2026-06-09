import payment.PaymentProcessor;
import payment.interfaces.PaymentMethod;
import payment.interfaces.PaymentNotifier;
import payment.interfaces.PaymentRepository;
import payment.methods.BitcoinPayment;
import payment.methods.CreditCardPayment;
import payment.methods.PayPalPayment;
import payment.methods.GoPayPayment;
import payment.services.DatabaseRepository;
import payment.services.MockEmailNotifier;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Payment Gateway Refactored (Starter Template) ===");

        // Setup Services
        // Ambil kredensial menggunakan System.getenv() atau gunakan default untuk testing lokal
        String dbUrl = System.getenv("DB_URL");
        if (dbUrl == null || dbUrl.isEmpty()) {
            dbUrl = "jdbc:mysql://localhost:3306/payment_db?createDatabaseIfNotExist=true";
        }
        
        String dbUser = System.getenv("DB_USER");
        if (dbUser == null || dbUser.isEmpty()) {
            dbUser = "root";
        }
        
        String dbPassword = System.getenv("DB_PASSWORD");
        if (dbPassword == null) {
            dbPassword = "password_anda";
        }
        
        String smtpServer = System.getenv("SMTP_SERVER");
        if (smtpServer == null || smtpServer.isEmpty()) {
            smtpServer = "smtp.mailtrap.io";
        }

        System.out.println("[CONFIG] DB URL: " + dbUrl);
        System.out.println("[CONFIG] SMTP Server: " + smtpServer);

        DatabaseRepository repository = new DatabaseRepository(dbUrl, dbUser, dbPassword);
        MockEmailNotifier notifier = new MockEmailNotifier(smtpServer);

        // Setup Processor
        PaymentProcessor processor = new PaymentProcessor(repository, notifier);

        // Test Legacy Payment
        System.out.println("\n--- Menguji Pembayaran Credit Card (Legacy) ---");
        PaymentMethod creditCard = new CreditCardPayment("1234-5678-9012-3456", "123", "12/28");
        processor.processPayment(creditCard, 150000.00, "pelanggan@email.com");

        System.out.println("\n--- Menguji Pembayaran PayPal (Legacy) ---");
        PaymentMethod payPal = new PayPalPayment("pelanggan@email.com");
        processor.processPayment(payPal, 275000.00, "pelanggan@email.com");

        // Test Fitur Baru
        System.out.println("\n--- Menguji Pembayaran GoPay (Fitur Baru) ---");
        PaymentMethod goPay = new GoPayPayment("081234567890");
        processor.processPayment(goPay, 50000.00, "081234567890");

        System.out.println("\n--- Menguji Pembayaran Bitcoin (Fitur Baru) ---");
        PaymentMethod bitcoin = new BitcoinPayment("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa");
        processor.processPayment(bitcoin, 1200000.00, "pelanggan@email.com");
    }
}