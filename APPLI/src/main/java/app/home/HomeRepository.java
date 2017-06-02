package app;

import org.springframework.data.repository.CrudRepository;
import app.Home;

// This will be AUTO IMPLEMENTED by Spring into a Bean called homeRepository
// CRUD refers Create, Read, Update, Delete

public interface HomeRepository extends CrudRepository<Home, Long> {

}
