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
        // Proses pembayaran sesuai metode yang dipilih
        method.pay(amount);
        
        // Simpan transaksi ke database lewat repository
        repository.saveTransaction(amount, method.getClass().getSimpleName());
        
        // Kirim notifikasi lewat notifier
        notifier.sendNotification(amount, customerContact);
    }
}