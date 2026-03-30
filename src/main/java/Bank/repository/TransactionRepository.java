package Bank.repository;

import Bank.model.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository {

    void save(Transaction transaction);
    List<Transaction> findByAccountId(String accountId);

    List<Transaction> findByFromAccountIdAndDate(String accountId, LocalDate date);
}
