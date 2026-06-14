package payment.services;

import payment.interfaces.TransactionStore;
import payment.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTransactionStore implements TransactionStore {
    private List<Transaction> transactions;

    public InMemoryTransactionStore() {
        this.transactions = new ArrayList<>();
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public boolean removeTransaction(int id) {
        return transactions.removeIf(t -> t.getId() == id);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    @Override
    public Transaction getTransactionById(int id) {
        return transactions.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void clearAllTransactions() {
        transactions.clear();
    }

    @Override
    public int getTransactionCount() {
        return transactions.size();
    }
}
