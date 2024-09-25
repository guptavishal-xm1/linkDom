package com.linkdom.services;

import com.linkdom.Entity.User;
import com.linkdom.repo.userRepo;
import org.springframework.stereotype.Service;

@Service
public class userServices { // service class for user entity
    final userRepo repo;


    public userServices(userRepo repo) {
        this.repo = repo;
    }

    public boolean saveUser(User user) {
        try{
            repo.save(user);
            return true;
        } catch (Exception e) {
            e.fillInStackTrace();
            return false;
        }
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username);
    }
    public boolean isUserExist(String email) {
        User user = repo.findByEmail(email);
        return user != null;
    }

    public User getUser(String email) {
         return repo.findByEmail(email);
    }

    public boolean isUserNameExist(String username) {
        return repo.existsByUsername(username);
    }

}
