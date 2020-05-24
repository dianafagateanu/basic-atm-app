package com.demo.atm.mapper;

import com.demo.atm.domain.Account;
import com.demo.atm.model.AccountModel;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountModel fromAccountEntityToModel(Account account) {
        return AccountModel.builder()
                .accountName(account.getAccountName())
                .currency(account.getCurrency().getName())
                .balance(account.getBalance())
                .build();
    }
}
