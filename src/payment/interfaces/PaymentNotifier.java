package payment.interfaces;

public interface PaymentNotifier {
    void sendNotification(double amount, String recipient);
}