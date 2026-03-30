package Bank.service;

import Bank.model.Account;
import Bank.model.Transaction;
import Bank.repository.AccountRepository;
import Bank.repository.TransactionRepository;

import java.time.LocalDate;
import java.util.List;

public class BankService {

    private final AccountRepository accorepo;
    private final TransactionRepository tranrepo;

    private static final int  DAILY_TRANSACTION_LIMIT = 3;
    private static final double OTP_THRESHOLD = 1000;
    private static final int SUSPICIOUS = 5;

    public BankService(AccountRepository accorepo, TransactionRepository tranrepo) {
        this.accorepo = accorepo;
        this.tranrepo = tranrepo;

    }

    public void transfer(String fromid, String toid, double amount) {

        //validar cuentas
        Account from = accorepo.findById(fromid)
                .orElseThrow(() -> new RuntimeException("Cuenta origen no encontrada"));

        Account to = accorepo.findById(toid)
                .orElseThrow(() -> new RuntimeException("Cuenta destino no encontrada"));
        //saldo insuficiente
        if (from.getBalance() < amount) {
            throw new RuntimeException("Saldo insuficiente");
        }

        //bloqueo automatico por actividad sospechosa
        if(from.isBlocked())
        {
            throw new RuntimeException("Cuenta bloqueada por actividad sospechosa");
        }

        // OTP si supera el umbral
        if (amount > OTP_THRESHOLD) {
            throw new RuntimeException("OTP requerido");
        }

        //Deteccion de actiivdad sospechosa
        List<Transaction> history = tranrepo.findByAccountId(fromid);

        if (history.size() >= SUSPICIOUS) {
            from.block();
            accorepo.save(from);
            throw new RuntimeException("Actividad sospechosa detectada");
        }

        //Limite diario
        List<Transaction> todayTransactions = tranrepo.findByFromAccountIdAndDate(fromid, LocalDate.now());
        if (todayTransactions.size() >= DAILY_TRANSACTION_LIMIT)
        {
            throw new RuntimeException("Limite diario excedido");

        }

        //ejecutar transferencias
        from.debit(amount);
        to.credit(amount);

        accorepo.save(from);
        accorepo.save(to);

        Transaction trans = new Transaction(fromid, toid, amount);
        tranrepo.save(trans);
    }

}
