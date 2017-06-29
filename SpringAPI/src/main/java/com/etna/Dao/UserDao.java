package com.etna.Dao;

import com.etna.Entity.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by quentin on 29/06/2017.
 */

@Repository
public class UserDao {

    private static Map<Integer, User> users;

    static {

        users = new HashMap<Integer, User>() {

            {
                put(1, new User(1, "quentin@mail.com", "Quentin", "Le Gal", "Canada"));
                put(2, new User(2, "lucas@mail.com","Lucas", "Pointurier", "France"));
                put(3, new User(3, "seb@mail.com","Sebastien", "Labegore", "France"));
            }

        };
    }

    public Collection<User> getAllUsers() {
        return this.users.values();
    }

    public User getUserById(Integer id) {
        return this.users.get(id);
    }

    public void removeUserById(int id) {
        this.users.remove(id);
    }

    public void updateUser(User user) {
        User usr = this.users.get(user.getId());
        //usr.setEmail(user.getEmail());
        usr.setCountry(user.getCountry());
        usr.setFirstname(user.getFirstname());
        usr.setLastname(user.getLastname());
    }

    public void insertUser(User user) {
        this.users.put(user.getId(), user);
    }
}
