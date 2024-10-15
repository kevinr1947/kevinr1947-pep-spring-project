package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.*;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;


    /**
     * Constructor - Dependency Injection.
     * 
     * @param accountRepository - JpaRepository for accessing the 'account' table in the database.
     */
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    /**
     * Add a new account to the database.
     *
     * @param account - Account object representing a new account to be added to the database.
     * @return the newly registered account if successful, including the account_id. 
     */
    public Account createAccount(Account account) {
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new DuplicateUsernameException("Username already taken.");
        }
        if (account.getUsername().length() < 1) {
            throw new InvalidInputException("Username cannot be blank.");
        }
        if (account.getPassword().length() < 4) {
            throw new InvalidInputException("Password must be at least 4 characters.");
        }

        return accountRepository.save(account);
    }

    /**
     * Validate login with account retrieval by username and password.
     * 
     * @param account - an Account object representing a login attempt.
     * @return the existing account if the query is successful, including the account_id.
     */
    public Account loginAccount(Account account) {
        if (!accountRepository.existsByUsernameAndPassword(account.getUsername(), account.getPassword())) {
            System.out.println("service condition checked");
            throw new InvalidLoginException("Username and password provided do not match an existing account.");
        }
        return accountRepository.getByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
}
