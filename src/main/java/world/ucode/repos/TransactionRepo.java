package world.ucode.repos;

import world.ucode.domain.Transaction;
import org.springframework.data.repository.CrudRepository;
import world.ucode.domain.Wallet;
//
//import javax.transaction.Transaction;
import java.util.List;

public interface TransactionRepo extends CrudRepository<Transaction, Long> {
    List<Transaction> findByWalletName(Wallet wallet);
}