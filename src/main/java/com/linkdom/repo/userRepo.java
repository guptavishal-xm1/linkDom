package com.linkdom.repo;

import com.linkdom.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepo extends JpaRepository<User, Long> {
    public User findByEmail(String email);
    boolean existsByUsername(String username);
    User findByUsername(String username);
}
