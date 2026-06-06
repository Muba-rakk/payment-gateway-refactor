package payment;

import payment.interfaces.PaymentMethod;
import payment.interfaces.PaymentNotifier;
import payment.interfaces.PaymentRepository;

public class PaymentProcessor {
    // TODO: Deklarasikan variabel private untuk PaymentRepository dan PaymentNotifier di sini

    // TODO: Buat constructor untuk melakukan injeksi PaymentRepository dan PaymentNotifier (DIP)

    public void processPayment(PaymentMethod method, double amount, String customerContact) {
        // TODO: 1. Proses pembayaran spesifik (Polimorfisme)
        
        // TODO: 2. Simpan transaksi ke database melalui repository
        
        // TODO: 3. Kirim notifikasi melalui notifier
    }
}