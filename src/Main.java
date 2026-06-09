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
        System.out.println("=== Payment Gateway Refactored (Starter Template) ===");

        // TODO: Setup Services
        // - Ambil kredensial menggunakan System.getenv() atau hardcode untuk testing lokal
        // - Buat instance DatabaseRepository dan MockEmailNotifier

        // TODO: Setup Processor
        // - Buat instance PaymentProcessor dan injeksi services tersebut

        // TODO: Test Legacy Payment
        // - Buat objek CreditCardPayment dan proses pembayarannya

        // TODO: Test Fitur Baru
        // - Buat objek GoPayPayment dan proses pembayarannya
    }
}