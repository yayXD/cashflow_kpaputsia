package world.ucode.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import world.ucode.domain.Category;
import world.ucode.domain.Currency;

@Repository
public interface CategoryRepo  extends CrudRepository<Category, Integer> {
    Category findByCategoryName(String categoryName);
}
