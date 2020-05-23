package com.demo.atm.service;

import com.demo.atm.domain.Account;
import com.demo.atm.model.CardDetailsModel;
import com.demo.atm.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.demo.atm.utils.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Optional <Account> findAccountByCardDetails(CardDetailsModel cardDetailsModel) {
        return accountRepository.findByCardNumberAndPinNumber(cardDetailsModel.getCardNumber(), cardDetailsModel.getPinNumber());
    }

    public String getAccountClientFullName(String username) {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        return accountOptional.map(Account::getFullName).orElse(SYSTEM_ADMIN);
    }
}
