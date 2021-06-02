package world.ucode.repos;

import org.springframework.stereotype.Repository;
import world.ucode.domain.Registration;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface RegistrationRepo extends CrudRepository<Registration, Long> {
    Registration findByUsername(String username);

}
