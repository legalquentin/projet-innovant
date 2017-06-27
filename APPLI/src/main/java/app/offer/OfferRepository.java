package app;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface OfferRepository extends CrudRepository<Offer, Long> {
  @Query(value = "SELECT * FROM Offer where uuid = :uuid", nativeQuery=true)
    Offer findByUuid(@Param("uuid") String uuid);
}
