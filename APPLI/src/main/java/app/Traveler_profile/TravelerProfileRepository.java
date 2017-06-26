package app.Traveler_profile;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelerProfileRepository extends CrudRepository<TravelerProfile, Long> {


}