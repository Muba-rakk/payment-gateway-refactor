package payment.interfaces;

public interface PaymentRepository {
    void saveTransaction(double amount, String methodType);
}