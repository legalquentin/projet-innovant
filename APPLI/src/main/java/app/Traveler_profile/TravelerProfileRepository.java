package app;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import app.TravelerProfile;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete


public interface TravelerProfileRepository extends CrudRepository<TravelerProfile, Long> {


}