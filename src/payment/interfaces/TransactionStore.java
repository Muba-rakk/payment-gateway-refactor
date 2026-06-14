package payment.interfaces;

import payment.model.Transaction;
import java.util.List;

public interface TransactionStore {
    void addTransaction(Transaction transaction);
    boolean removeTransaction(int id);
    List<Transaction> getAllTransactions();
    Transaction getTransactionById(int id);
    void clearAllTransactions();
    int getTransactionCount();
}
