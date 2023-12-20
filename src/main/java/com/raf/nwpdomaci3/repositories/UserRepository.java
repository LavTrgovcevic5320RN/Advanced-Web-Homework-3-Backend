package com.raf.nwpdomaci3.repositories;

import com.raf.nwpdomaci3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  UserRepository extends JpaRepository<User, Long> {
    //public User findByUsername(String username);
    public User findByEmail(String email);
}
