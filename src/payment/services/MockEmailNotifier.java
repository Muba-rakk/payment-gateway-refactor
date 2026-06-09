package payment.services;

import payment.interfaces.PaymentNotifier;

public class MockEmailNotifier implements PaymentNotifier {
    private String smtpServer;

    public MockEmailNotifier(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    @Override
    public void sendNotification(double amount, String recipient) {
        String message = String.format("Pembayaran sebesar Rp%.2f sukses dikirim ke %s melalui server %s.", amount, recipient, smtpServer);
        System.out.println("[EMAIL NOTIFICATION] Mengirim email: " + message);
    }
}