package payment.services;

import payment.interfaces.PaymentNotifier;

public class MockEmailNotifier implements PaymentNotifier {
    // TODO: Deklarasikan private String smtpServer

    // TODO: Buat constructor untuk menginisialisasi smtpServer

    @Override
    public void sendNotification(double amount, String recipient) {
        // TODO: Print simulasi pesan pengiriman email
        System.out.println("[TODO] sendNotification belum diimplementasikan.");
    }
}