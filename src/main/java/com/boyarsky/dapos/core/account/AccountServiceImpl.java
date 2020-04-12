package com.boyarsky.dapos.core.account;

import com.boyarsky.dapos.core.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository repository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account get(AccountId accountId) {
        Account account = repository.find(accountId);
        if (account == null) {
            throw new NotFoundException("Account with id " + accountId.toString() + " does not exist");
        }
        return account;
    }

    @Override
    public List<Account> getAll() {
        return repository.getAll();
    }

    @Override
    public void save(Account account) {
        repository.save(account);
    }
}