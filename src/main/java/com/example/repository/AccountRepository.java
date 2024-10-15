package com.example.repository;

import com.example.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    boolean existsByUsername(String username);
    boolean existsByUsernameAndPassword(String username, String password);
    Account getByUsernameAndPassword(String username, String password);
}
