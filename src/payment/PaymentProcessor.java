package payment;

import payment.interfaces.PaymentMethod;
import payment.interfaces.PaymentNotifier;
import payment.interfaces.PaymentRepository;
import payment.interfaces.TransactionStore;
import payment.model.Transaction;

public class PaymentProcessor {
    private PaymentRepository repository;
    private PaymentNotifier notifier;
    private TransactionStore transactionStore;

    // Constructor dengan TransactionStore (fitur baru)
    public PaymentProcessor(PaymentRepository repository, PaymentNotifier notifier, TransactionStore transactionStore) {
        this.repository = repository;
        this.notifier = notifier;
        this.transactionStore = transactionStore;
    }

    // Constructor lama tetap tersedia untuk backward compatibility
    public PaymentProcessor(PaymentRepository repository, PaymentNotifier notifier) {
        this(repository, notifier, null);
    }

    public Transaction processPayment(PaymentMethod method, double amount, String tecnicalPaymentDetail, String customerContact) {
        // Proses pembayaran sesuai metode yang dipilih
        method.pay(amount);
        
        // Simpan transaksi ke database lewat repository
        repository.saveTransaction(amount, method.getClass().getSimpleName());
        
        // Kirim notifikasi lewat notifier
        notifier.sendNotification(amount, customerContact);

        // Simpan ke in-memory store jika tersedia
        Transaction transaction = new Transaction(amount, method.getClass().getSimpleName(), customerContact);
        if (transactionStore != null) {
            transactionStore.addTransaction(transaction);
        }
        
        return transaction;
    }

    // Method lama tetap tersedia
    public void processPayment(PaymentMethod method, double amount, String customerContact) {
        processPayment(method, amount, "", customerContact);
    }
}
