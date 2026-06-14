package payment.ui;

import payment.PaymentProcessor;
import payment.interfaces.TransactionStore;
import payment.methods.BitcoinPayment;
import payment.methods.CreditCardPayment;
import payment.methods.GoPayPayment;
import payment.methods.PayPalPayment;
import payment.model.Transaction;

import java.util.Scanner;

public class PaymentMenu {
    private PaymentProcessor processor;
    private TransactionStore transactionStore;
    private Scanner scanner;

    public PaymentMenu(PaymentProcessor processor, TransactionStore transactionStore, Scanner scanner) {
        this.processor = processor;
        this.transactionStore = transactionStore;
        this.scanner = scanner;
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readIntInput("Pilih opsi: ");

            switch (choice) {
                case 1:
                    menuPembayaran();
                    break;
                case 2:
                    viewTransactions();
                    break;
                case 3:
                    deleteTransaction();
                    break;
                case 4:
                    clearAllTransactions();
                    break;
                case 5:
                    searchTransaction();
                    break;
                case 0:
                    running = false;
                    System.out.println("\n[INFO] Keluar dari aplikasi. Sampai jumpa!");
                    break;
                default:
                    System.out.println("\n[ERROR] Opsi tidak valid. Silakan coba lagi.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         PAYMENT GATEWAY MENU         ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1  │ Proses Pembayaran              ║");
        System.out.println("║  2  │ Lihat Riwayat Transaksi        ║");
        System.out.println("║  3  │ Hapus Transaksi                ║");
        System.out.println("║  4  │ Hapus Semua Transaksi          ║");
        System.out.println("║  5  │ Cari Transaksi Berdasarkan ID  ║");
        System.out.println("║  ─  │                                ║");
        System.out.println("║  0  │ Keluar                         ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    private void menuPembayaran() {
        System.out.println("\n---------- METODE PEMBAYARAN ----------");
        System.out.println("  1. Credit Card");
        System.out.println("  2. PayPal");
        System.out.println("  3. GoPay");
        System.out.println("  4. Bitcoin");
        System.out.println("  0. Kembali ke Menu Utama");
        System.out.println("---------------------------------------");

        int metode = readIntInput("Pilih metode pembayaran: ");

        if (metode == 0) return;

        if (metode < 1 || metode > 4) {
            System.out.println("\n[ERROR] Metode tidak valid.");
            return;
        }

        System.out.println("\n--- Detail Pembayaran ---");
        double amount = readDoubleInput("Masukkan jumlah pembayaran (Rp): ");
        scanner.nextLine(); // consume newline
        String customerContact = readStringInput("Masukkan email/nomor pelanggan: ");

        switch (metode) {
            case 1:
                System.out.println("\n[Credit Card]");
                String cardNum = readStringInput("Nomor Kartu: ");
                String cvv = readStringInput("CVV: ");
                String exp = readStringInput("Expiry Date (MM/YY): ");
                processor.processPayment(new CreditCardPayment(cardNum, cvv, exp), amount, customerContact);
                break;
            case 2:
                System.out.println("\n[PayPal]");
                String email = readStringInput("Email PayPal: ");
                processor.processPayment(new PayPalPayment(email), amount, customerContact);
                break;
            case 3:
                System.out.println("\n[GoPay]");
                String phone = readStringInput("Nomor Telepon: ");
                processor.processPayment(new GoPayPayment(phone), amount, customerContact);
                break;
            case 4:
                System.out.println("\n[Bitcoin]");
                String wallet = readStringInput("Wallet Address: ");
                processor.processPayment(new BitcoinPayment(wallet), amount, customerContact);
                break;
        }

        System.out.println("\n[SUKSES] Transaksi berhasil diproses!");
    }

    private void viewTransactions() {
        System.out.println("\n══════════ DAFTAR TRANSAKSI ══════════");
        var transactions = transactionStore.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("| Tidak ada transaksi tersimpan.     |");
        } else {
            System.out.println("╔═════╦══════════════╦═══════════════╦══════════════════════════╦══════════════════════════╗");
            System.out.println("║ ID  │   METODE     │     JUMLAH    │        KONTAK              │      WAKTU               ║");
            System.out.println("╠═════╬══════════════╬═══════════════╬══════════════════════════╬══════════════════════════╣");
            for (Transaction t : transactions) {
                System.out.println(t.toString());
            }
            System.out.println("╚═════╩══════════════╩═══════════════╩══════════════════════════╩══════════════════════════╝");
            System.out.println("Total Transaksi: " + transactionStore.getTransactionCount());
        }
        System.out.println("═══════════════════════════════════════");
    }

    private void deleteTransaction() {
        viewTransactions();
        if (transactionStore.getTransactionCount() == 0) {
            return;
        }

        int id = readIntInput("Masukkan ID transaksi yang ingin dihapus (0 untuk batal): ");
        if (id == 0) return;

        boolean removed = transactionStore.removeTransaction(id);
        if (removed) {
            System.out.println("[SUKSES] Transaksi dengan ID " + id + " berhasil dihapus.");
        } else {
            System.out.println("[ERROR] Transaksi dengan ID " + id + " tidak ditemukan.");
        }
    }

    private void clearAllTransactions() {
        if (transactionStore.getTransactionCount() == 0) {
            System.out.println("[INFO] Tidak ada transaksi untuk dihapus.");
            return;
        }

        System.out.println("\n[PERINGATAN] Anda akan menghapus semua transaksi!");
        String confirm = readStringInput("Ketik 'YA' untuk konfirmasi: ");
        if ("YA".equals(confirm)) {
            transactionStore.clearAllTransactions();
            System.out.println("[SUKSES] Semua transaksi telah dihapus.");
        } else {
            System.out.println("[INFO] Penghapusan dibatalkan.");
        }
    }

    private void searchTransaction() {
        int id = readIntInput("Masukkan ID transaksi yang dicari: ");
        Transaction t = transactionStore.getTransactionById(id);
        if (t != null) {
            System.out.println("\n══════════ TRANSAKSI DITEMUKAN ══════════");
            System.out.println("  ID       : " + t.getId());
            System.out.println("  Metode   : " + t.getMethodName());
            System.out.println("  Jumlah   : Rp " + String.format("%.2f", t.getAmount()));
            System.out.println("  Kontak   : " + t.getCustomerContact());
            System.out.println("  Waktu    : " + t.getFormattedTimestamp());
            System.out.println("════════════════════════════════════════");
        } else {
            System.out.println("[ERROR] Transaksi dengan ID " + id + " tidak ditemukan.");
        }
    }

    // Helper methods untuk input
    private int readIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Input harus berupa angka. Silakan coba lagi.");
            }
        }
    }

    private double readDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Input harus berupa angka. Silakan coba lagi.");
            }
        }
    }

    private String readStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
