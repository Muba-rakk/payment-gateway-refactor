package payment.methods;

import payment.interfaces.PaymentMethod;

public class CreditCardPayment implements PaymentMethod {
    private String cardNumber;
    private String cvv;
    private String expiryDate;

    public CreditCardPayment(String cardNumber, String cvv, String expiryDate) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    @Override
    public void pay(double amount) {
        System.out.println(String.format("Memproses CC... dengan kartu %s (CVV: %s, Exp: %s) sebesar Rp%.2f", cardNumber, cvv, expiryDate, amount));
    }
}