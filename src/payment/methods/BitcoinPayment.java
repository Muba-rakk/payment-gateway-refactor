package payment.methods;

import payment.interfaces.PaymentMethod;

public class BitcoinPayment implements PaymentMethod {
    private String walletAddress;

    public BitcoinPayment(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    @Override
    public void pay(double amount) {
        System.out.println(String.format("Memproses Bitcoin... dengan wallet %s sebesar Rp%.2f", walletAddress, amount));
    }
}