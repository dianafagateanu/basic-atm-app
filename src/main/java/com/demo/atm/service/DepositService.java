package com.demo.atm.service;

import com.demo.atm.domain.Account;
import com.demo.atm.domain.Banknote;
import com.demo.atm.domain.Transaction;
import com.demo.atm.domain.TransactionType;
import com.demo.atm.model.DepositBanknoteModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class DepositService {

    private final AccountService accountService;
    private final BanknoteService banknoteService;
    private final TransactionService transactionService;
    private final TransactionBanknoteDetailsService transactionBanknoteDetailsService;
    private final AtmBoxService atmBoxService;

    public Map <Long, Long> getBanknotesTypeForClientAccountCurrency(String username) {
        Account account = accountService.getAccountByUsername(username);
        List <Banknote> banknotes = banknoteService.findAllBanknotesByCurrency(account.getCurrency());
        return banknotes
                .stream()
                .collect(Collectors.toMap(Banknote::getBillValue, banknote -> 0L));
    }

    public Long processDepositOperation(DepositBanknoteModel depositBanknoteModel, Account account) {
        Long amountDeposited = calculateDepositedAmount(depositBanknoteModel.getDepositBanknoteMap());

        accountService.depositAmount(amountDeposited, account);

        Transaction transaction = transactionService.createTransaction(TransactionType.DEPOSIT, amountDeposited, account);
        transactionBanknoteDetailsService.createTransactionBanknoteDetailsSet(depositBanknoteModel, account.getCurrency(), transaction);

        atmBoxService.updateAtmBoxes(depositBanknoteModel, account.getCurrency(), TransactionType.DEPOSIT);

        return amountDeposited;
    }

    private Long calculateDepositedAmount(Map <Long, Long> depositBanknoteMap) {
        return depositBanknoteMap.entrySet().stream()
                .mapToLong(depositBanknoteEntry -> depositBanknoteEntry.getKey() * depositBanknoteEntry.getValue())
                .sum();
    }
}
