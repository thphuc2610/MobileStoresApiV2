package com.example.store.repository;

import com.example.store.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //====== Exists Username ADD    2024/10/06 PhucHT START ======//
    public boolean existsByUsername(String username);
    //====== Exists Username ADD    2024/10/06 PhucHT END   ======//

    Optional<User> findByUsername(String username);
    User findByUsernameAndPassword(String usn, String pass);
}
