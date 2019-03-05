package com.wari.eurekaServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final UserService userService; // <1>

    @Autowired
    public AccountService(AccountRepository ar, UserService us) { // <2>
        this.accountRepository = ar;
        this.userService = us;
    }

    public List<Account> getUserAccounts() {
        // <3>
        return Optional.ofNullable(userService.getAuthenticatedUser())
                .map(u -> accountRepository.findAccountsByUsername(u.getUsername()))
                .orElse(Collections.emptyList());
    }
}