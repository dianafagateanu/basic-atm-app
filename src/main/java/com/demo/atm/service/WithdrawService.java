package com.demo.atm.service;

import com.demo.atm.domain.*;
import com.demo.atm.model.DepositBanknoteModel;
import com.demo.atm.model.WithdrawModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class WithdrawService {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final TransactionBanknoteDetailsService transactionBanknoteDetailsService;
    private final AtmBoxService atmBoxService;
    private final BanknoteService banknoteService;

    public void processWithdrawOperation(WithdrawModel withdrawModel, Account account) {
        Long amountWithdrawn = withdrawModel.getAmountWithdrawn();

        DepositBanknoteModel depositBanknoteModel = splitAmountWithdrawnInBillValues(amountWithdrawn, account.getCurrency());

        accountService.withdrawAmount(amountWithdrawn, account);
        Transaction transaction = transactionService.createTransaction(TransactionType.WITHDRAW, amountWithdrawn, account);
        transactionBanknoteDetailsService.createTransactionBanknoteDetailsSet(depositBanknoteModel, account.getCurrency(), transaction);
        atmBoxService.updateAtmBoxes(depositBanknoteModel, account.getCurrency(), TransactionType.WITHDRAW);
    }

    private DepositBanknoteModel splitAmountWithdrawnInBillValues(Long amountWithdrawn, Currency currency) {
        List <Banknote> sortedBanknoteList = banknoteService.findAllByCurrencyOrderByBillValueDesc(currency);
        List <Long> sortedBillValues = sortedBanknoteList.stream()
                .map(Banknote::getBillValue)
                .collect(Collectors.toList());
        DepositBanknoteModel depositBanknoteModel = new DepositBanknoteModel();
        depositBanknoteModel.setDepositBanknoteMap(new LinkedHashMap <>());
        for (long billValue : sortedBillValues) {
            if (amountWithdrawn / billValue > 0) {
                depositBanknoteModel.getDepositBanknoteMap().put(billValue, amountWithdrawn / billValue);
            }
            amountWithdrawn = amountWithdrawn % billValue;
        }
        return depositBanknoteModel;
    }
}
