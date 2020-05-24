package com.demo.atm.service;

import com.demo.atm.SamplesGenerator;
import com.demo.atm.domain.*;
import com.demo.atm.model.DepositBanknoteModel;
import com.demo.atm.model.WithdrawModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WithdrawServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private TransactionBanknoteDetailsService transactionBanknoteDetailsService;

    @Mock
    private AtmBoxService atmBoxService;

    @Mock
    private BanknoteService banknoteService;

    private WithdrawService withdrawService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        withdrawService = new WithdrawService(accountService, transactionService, transactionBanknoteDetailsService,
                atmBoxService, banknoteService);
    }

    @Test
    void processWithdrawOperationTest() {
        Long amountWithdrawn = 1560L;
        WithdrawModel withdrawModel = new WithdrawModel();
        withdrawModel.setAmountWithdrawn(amountWithdrawn);

        Currency currency = SamplesGenerator.getCurrencySample();
        Account account = SamplesGenerator.getAccountSample(currency);
        Transaction transaction = SamplesGenerator.getTransactionSample(TransactionType.WITHDRAW, amountWithdrawn, currency, account);

        doNothing().when(accountService).withdrawAmount(amountWithdrawn, account);
        when(transactionService.createTransaction(TransactionType.WITHDRAW, amountWithdrawn, account)).thenReturn(transaction);
        doNothing().when(atmBoxService).updateAtmBoxes(any(DepositBanknoteModel.class), eq(account.getCurrency()), eq(TransactionType.WITHDRAW));

        withdrawService.processWithdrawOperation(withdrawModel, account);

        verify(accountService, times(1)).withdrawAmount(anyLong(), any(Account.class));
        verify(transactionService, times(1)).createTransaction(any(TransactionType.class), anyLong(), any(Account.class));
        verify(atmBoxService, times(1)).updateAtmBoxes(any(DepositBanknoteModel.class), any(Currency.class), any(TransactionType.class));
    }

    @Test
    void splitAmountWithdrawnInBillValuesTest_1() {
        Map <Long, Long> depositBanknoteMap = new LinkedHashMap <>();
        depositBanknoteMap.put(100L, 1L);

        Long amountWithdrawn = 100L;

        verifySplitAMountWithdrawnInBillValues(depositBanknoteMap, amountWithdrawn);
    }

    @Test
    void splitAmountWithdrawnInBillValuesTest_2() {
        Map <Long, Long> depositBanknoteMap = new LinkedHashMap <>();
        depositBanknoteMap.put(50L, 1L);

        Long amountWithdrawn = 50L;

        verifySplitAMountWithdrawnInBillValues(depositBanknoteMap, amountWithdrawn);
    }

    @Test
    void splitAmountWithdrawnInBillValuesTest_3() {
        Map <Long, Long> depositBanknoteMap = new LinkedHashMap <>();
        depositBanknoteMap.put(10L, 1L);

        Long amountWithdrawn = 10L;

        verifySplitAMountWithdrawnInBillValues(depositBanknoteMap, amountWithdrawn);
    }

    @Test
    void splitAmountWithdrawnInBillValuesTest_4() {
        Map <Long, Long> depositBanknoteMap = new LinkedHashMap <>();
        depositBanknoteMap.put(100L, 1L);
        depositBanknoteMap.put(10L, 1L);

        Long amountWithdrawn = 110L;

        verifySplitAMountWithdrawnInBillValues(depositBanknoteMap, amountWithdrawn);
    }

    @Test
    void splitAmountWithdrawnInBillValuesTest_5() {
        Map <Long, Long> depositBanknoteMap = new LinkedHashMap <>();
        depositBanknoteMap.put(100L, 1L);
        depositBanknoteMap.put(50L, 1L);

        Long amountWithdrawn = 150L;

        verifySplitAMountWithdrawnInBillValues(depositBanknoteMap, amountWithdrawn);
    }

    @Test
    void splitAmountWithdrawnInBillValuesTest_6() {
        Map <Long, Long> depositBanknoteMap = new LinkedHashMap <>();
        depositBanknoteMap.put(50L, 1L);
        depositBanknoteMap.put(10L, 1L);

        Long amountWithdrawn = 60L;

        verifySplitAMountWithdrawnInBillValues(depositBanknoteMap, amountWithdrawn);
    }

    @Test
    void splitAmountWithdrawnInBillValuesTest_7() {
        Map <Long, Long> depositBanknoteMap = new LinkedHashMap <>();
        depositBanknoteMap.put(100L, 1L);
        depositBanknoteMap.put(50L, 1L);
        depositBanknoteMap.put(10L, 1L);

        Long amountWithdrawn = 160L;

        verifySplitAMountWithdrawnInBillValues(depositBanknoteMap, amountWithdrawn);
    }

    @Test
    void splitAmountWithdrawnInBillValuesTest_8() {
        Map <Long, Long> depositBanknoteMap = new LinkedHashMap <>();
        depositBanknoteMap.put(100L, 15L);
        depositBanknoteMap.put(50L, 1L);
        depositBanknoteMap.put(10L, 1L);

        Long amountWithdrawn = 1560L;

        verifySplitAMountWithdrawnInBillValues(depositBanknoteMap, amountWithdrawn);
    }

    private void verifySplitAMountWithdrawnInBillValues(Map <Long, Long> depositBanknoteMap, Long amountWithdrawn) {
        Currency currency = SamplesGenerator.getCurrencySample();
        List <Banknote> sortedBanknoteList = SamplesGenerator.getSortedBanknoteListSample(currency);

        when(banknoteService.findAllByCurrencyOrderByBillValueDesc(currency)).thenReturn(sortedBanknoteList);

        DepositBanknoteModel actualResult = withdrawService.splitAmountWithdrawnInBillValues(amountWithdrawn, currency);

        assertEquals(depositBanknoteMap, actualResult.getDepositBanknoteMap());
    }
}