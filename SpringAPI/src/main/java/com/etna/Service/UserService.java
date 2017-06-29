package com.etna.Service;

import com.etna.Dao.UserDao;
import com.etna.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by quentin on 29/06/2017.
 */

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public Collection<User> getAllUsers() {
        return this.userDao.getAllUsers();
    }

    public User getUserById(Integer id) {
        return this.userDao.getUserById( id);
    }

    public void removeUserById(int id) {
        this.userDao.removeUserById(id);
    }

    public void updateUser(User user) {
       this.userDao.updateUser(user);
    }

    public void insertUser(User user) {
        this.userDao.insertUser(user);
    }
}
