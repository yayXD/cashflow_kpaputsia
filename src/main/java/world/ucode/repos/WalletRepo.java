package world.ucode.repos;

import org.springframework.stereotype.Repository;
import world.ucode.domain.Registration;
import world.ucode.domain.Wallet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface WalletRepo extends CrudRepository<Wallet, Long> {
    Wallet findByName(String name);
    List<Wallet> findByOwnerLogin(Registration registration);
    Wallet findByItemNumber(int itemNumber);

}
