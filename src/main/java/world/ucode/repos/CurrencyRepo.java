package world.ucode.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import world.ucode.domain.Currency;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface CurrencyRepo extends CrudRepository<Currency, Long> {
    Currency findByCurName(String curName);
}
