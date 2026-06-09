package payment.methods;

import payment.interfaces.PaymentMethod;

public class GoPayPayment implements PaymentMethod {
    private String phoneNumber;

    public GoPayPayment(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.println(String.format("Memproses GoPay... dengan nomor %s sebesar Rp%.2f", phoneNumber, amount));
    }
}