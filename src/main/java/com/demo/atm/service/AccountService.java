package com.demo.atm.service;

import com.demo.atm.domain.Account;
import com.demo.atm.exception.EntityNotFoundException;
import com.demo.atm.mapper.AccountMapper;
import com.demo.atm.model.AccountModel;
import com.demo.atm.model.CardDetailsModel;
import com.demo.atm.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.demo.atm.utils.Constants.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public Optional <Account> findAccountByCardDetails(CardDetailsModel cardDetailsModel) {
        return accountRepository.findByCardNumberAndPinNumber(cardDetailsModel.getCardNumber(), cardDetailsModel.getPinNumber());
    }

    public AccountModel getAccountModelForClient(String username) {
        Optional <Account> accountOptional = accountRepository.findByUsername(username);
        return accountOptional
                .map(accountMapper::fromAccountEntityToModel)
                .orElseThrow(() -> new EntityNotFoundException(ACCOUNT_EXCEPTION_MESSAGE_ERROR + username));
    }

    public String getAccountClientFullName(String username) {
        Optional <Account> accountOptional = accountRepository.findByUsername(username);
        return accountOptional
                .map(Account::getFullName)
                .orElse(SYSTEM_ADMIN);
    }

    public Account getAccountByUsername(String username) {
        Optional <Account> accountOptional = accountRepository.findByUsername(username);
        return accountOptional.orElseThrow(() -> new EntityNotFoundException(ACCOUNT_EXCEPTION_MESSAGE_ERROR + username));
    }

    void depositAmount(Long amount, Account account) {
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
        log.info("account with id {} was updated", account.getId());
    }
}
