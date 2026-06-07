package payment;

import payment.interfaces.PaymentMethod;
import payment.interfaces.PaymentNotifier;
import payment.interfaces.PaymentRepository;

public class PaymentProcessor {
    private PaymentRepository repository;
    private PaymentNotifier notifier;

    public PaymentProcessor(PaymentRepository repository, PaymentNotifier notifier) {
        this.repository = repository;
        this.notifier = notifier;
    }

    public void processPayment(PaymentMethod method, double amount, String customerContact) {
        // 1. Proses pembayaran spesifik (Polimorfisme)
        method.pay(amount);
        
        // 2. Simpan transaksi ke database melalui repository
        repository.saveTransaction(amount, method.getClass().getSimpleName());
        
        // 3. Kirim notifikasi melalui notifier
        notifier.sendNotification(amount, customerContact);
    }
}
