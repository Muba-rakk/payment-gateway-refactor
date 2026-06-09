package payment.methods;

import payment.interfaces.PaymentMethod;

public class PayPalPayment implements PaymentMethod {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Memproses PayPal... dengan email " + email + " sebesar Rp" + amount);
    }
}