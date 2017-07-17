package com.etna.Service;

//import com.etna.Dao.UserDao;
import com.etna.Entity.User;
import com.etna.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by quentin on 29/06/2017.
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(this.userRepository.findAll());
    }

    public User getUserById(String email) {
        return this.userRepository.findByEmail(email);
    }

    public void removeUserById(int id) {
        this.userRepository.delete(id);
    }

    public void updateUser(User user) {
        User usr = this.userRepository.findOne(user.getId());
        usr.setCountry(user.getCountry());
        usr.setFirstName(user.getFirstName());
        usr.setLastName(user.getLastName());
        this.userRepository.save(usr);
    }

    public void insertUser(User user) {
        this.userRepository.save(user);
    }
}
