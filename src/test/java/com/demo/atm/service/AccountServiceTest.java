package com.demo.atm.service;

import com.demo.atm.SamplesGenerator;
import com.demo.atm.domain.Account;
import com.demo.atm.domain.Currency;
import com.demo.atm.exception.EntityNotFoundException;
import com.demo.atm.mapper.AccountMapper;
import com.demo.atm.model.AccountModel;
import com.demo.atm.model.CardDetailsModel;
import com.demo.atm.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.demo.atm.utils.Constants.ACCOUNT_EXCEPTION_MESSAGE_ERROR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        accountService = new AccountService(accountRepository, accountMapper);
    }

    @Test
    void findAccountByCardDetails() {
        CardDetailsModel cardDetailsModel = SamplesGenerator.getCardDetailsModelSample();
        Currency currency = SamplesGenerator.getCurrencySample();
        Optional <Account> expectedAccountOptional = Optional.of(SamplesGenerator.getAccountSample(currency));

        when(accountRepository.findByCardNumberAndPinNumber(cardDetailsModel.getCardNumber(), cardDetailsModel.getPinNumber()))
                .thenReturn(expectedAccountOptional);

        Optional <Account> actualResult = accountService.findAccountByCardDetails(cardDetailsModel);

        verify(accountRepository, times(1)).findByCardNumberAndPinNumber(anyString(), anyString());

        assertEquals(expectedAccountOptional, actualResult);
    }

    @Test
    void getAccountModelForClientWhenAccountExists() {
        Currency currency = SamplesGenerator.getCurrencySample();
        Account account = SamplesGenerator.getAccountSample(currency);
        AccountModel expectedAccountModel = AccountModel.builder()
                .accountName(account.getAccountName())
                .balance(account.getBalance())
                .currency(account.getCurrency().getName())
                .build();
        String username = "ion_popescu";

        when(accountRepository.findByUsername(username)).thenReturn(Optional.of(account));
        when(accountMapper.fromAccountEntityToModel(account)).thenReturn(expectedAccountModel);

        AccountModel actualResult = accountService.getAccountModelForClient(username);

        verify(accountRepository, times(1)).findByUsername(anyString());
        verify(accountMapper, times(1)).fromAccountEntityToModel(any(Account.class));

        assertEquals(expectedAccountModel, actualResult);
    }

    @Test
    void getAccountModelForClientWhenAccountDoesNotExist() {
        String username = "ion_badea";
        String expectedExceptionMessage = ACCOUNT_EXCEPTION_MESSAGE_ERROR + username;

        when(accountRepository.findByUsername(username)).thenReturn(Optional.empty());

        Exception actualResult = assertThrows(EntityNotFoundException.class, () -> accountService.getAccountModelForClient(username));

        String actualExceptionMessage = actualResult.getMessage();

        verify(accountRepository, times(1)).findByUsername(anyString());
        verifyNoInteractions(accountMapper);

        assertTrue(actualExceptionMessage.contains(expectedExceptionMessage));
    }
}