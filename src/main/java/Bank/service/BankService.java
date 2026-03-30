package Bank.service;

import Bank.model.Account;
import Bank.model.Transaction;
import Bank.repository.AccountRepository;
import Bank.repository.TransactionRepository;

import java.time.LocalDate;
import java.util.List;

public class BankService {

    private static final int DAILY_LIMIT = 3;
    private final AccountRepository accorepo;
    private final TransactionRepository tranrepo;

    public BankService(AccountRepository accorepo, TransactionRepository tranrepo) {
        this.accorepo = accorepo;
        this.tranrepo = tranrepo;

    }

    public void transfer(String fromid, String toid, double amount) {
        Account from = accorepo.findById(fromid)
                .orElseThrow(() -> new RuntimeException("Cuenta Remitente no encontrada"));

        Account to = accorepo.findById(toid)
                .orElseThrow(() -> new RuntimeException("Cuenta destino no encontrada"));

        List<Transaction> todayTransactions = tranrepo.findByFromAccountIdAndDate(fromid, LocalDate.now());
        if (todayTransactions.size() >= DAILY_LIMIT)
        {
            throw new RuntimeException("Limite diario excedido");

        }

        if (from.getBalance() < amount) {
            throw new RuntimeException("Balance insuficiente");
        }

        from.debit(amount);
        to.credit(amount);

        accorepo.save(from);
        accorepo.save(to);

        Transaction trans = new Transaction(fromid, toid, amount);
        tranrepo.save(trans);
    }

}
