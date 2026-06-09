package payment.services;

import payment.interfaces.PaymentNotifier;

public class MockEmailNotifier implements PaymentNotifier {
    private String smtpServer;

    public MockEmailNotifier(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    @Override
    public void sendNotification(double amount, String recipient) {
        String message = String.format("Pembayaran sebesar Rp%.2f berhasil.", amount);
        System.out.println("[EMAIL NOTIFICATION] Mengirim email via " + smtpServer + " ke " + recipient + ": " + message);
    }
}