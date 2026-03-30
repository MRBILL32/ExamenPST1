package Bank;

import Bank.model.Account;
import Bank.model.Transaction;
import Bank.repository.AccountRepository;
import Bank.repository.TransactionRepository;
import Bank.service.BankService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BankServiceTest {

    private final AccountRepository accorepo = mock(AccountRepository.class);
    private final TransactionRepository tranrepo = mock(TransactionRepository.class);
    private final BankService bankService = new BankService(accorepo, tranrepo);

    //transferir entre cuentas
    @Test void shouldCreateAccount()
    {
        //inicio
        Account from = new Account("A1",1000);
        Account to = new Account("A2",500);

        when(accorepo.findById("A1")).thenReturn(Optional.of(from));
        when(accorepo.findById("A2")).thenReturn(Optional.of(to));

        //proceso
        bankService.transfer("A1", "A2", 200);

        //Resultante
        assertEquals(800,from.getBalance());
        assertEquals(700,to.getBalance());

        verify(tranrepo,times(1)).save(any(Transaction.class));
        verify(accorepo,times(1)).save(from);
        verify(accorepo,times(1)).save(to);


    }

    //validar saldo insuficiente
    @Test void shouldFailWhenInsufficientBalance()
    {
        Account from = new Account("A1",100);
        Account to = new Account("A2",500);

        when(accorepo.findById("A1")).thenReturn(Optional.of(from));
        when(accorepo.findById("A2")).thenReturn(Optional.of(to));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            bankService.transfer("A1", "A2", 200);
        });

        assertEquals("Balance insuficiente", ex.getMessage());
    }

    //Validar cuenta destino existe
    @Test void shouldFailWhenDestinationAccountDoesNotExist()
    {
        Account from = new Account("A1",1000);

        when(accorepo.findById("A1")).thenReturn(Optional.of(from));
        when(accorepo.findById("A2")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            bankService.transfer("A1", "A2", 200);
        });

        assertEquals("Cuenta destino no encontrada", ex.getMessage());

    }

    //Validar limite diario
    @Test void shouldFailWhenDailyLimitExceeded()
    {
        Account from = new Account("A1",1000);
        Account to = new Account("A2",500);

        when(accorepo.findById("A1")).thenReturn(Optional.of(from));
        when(accorepo.findById("A2")).thenReturn(Optional.of(to));

        List<Transaction> txs = Arrays.asList(
                new Transaction("A1", "A2", 100),
                new Transaction("A1", "A2", 100),
                new Transaction("A1", "A2", 100)
        );

        when(tranrepo.findByFromAccountIdAndDate(
                eq("A1"),
                any(LocalDate.class)
        )).thenReturn(txs);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            bankService.transfer("A1", "A2", 100);
        });

        assertEquals("Limite diario excedido", ex.getMessage());
    }

}
