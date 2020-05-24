package com.demo.atm.service;

import com.demo.atm.domain.Currency;
import com.demo.atm.domain.Transaction;
import com.demo.atm.domain.TransactionBanknoteDetails;
import com.demo.atm.model.DepositBanknoteModel;
import com.demo.atm.repository.TransactionBanknoteDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TransactionBanknoteDetailsService {

    private final BanknoteService banknoteService;
    private final TransactionBanknoteDetailsRepository transactionBanknoteDetailsRepository;

    public void createTransactionBanknoteDetailsSet(DepositBanknoteModel depositBanknoteModel, Currency currency, Transaction transaction) {
        depositBanknoteModel.getDepositBanknoteMap().forEach((depositBanknoteEntryKey, depositBanknoteEntryValue) -> {
            createNewTransactionBanknoteDetails(depositBanknoteEntryKey, depositBanknoteEntryValue, currency, transaction);
        });
    }

    private void createNewTransactionBanknoteDetails(Long billValue, Long countBills, Currency currency, Transaction transaction) {
        TransactionBanknoteDetails transactionBanknoteDetails = new TransactionBanknoteDetails();
        transactionBanknoteDetails.setCountBills(countBills);
        transactionBanknoteDetails.setBanknote(banknoteService.findByBillValueAndCurrency(billValue, currency));
        transactionBanknoteDetails.setTransaction(transaction);
        transactionBanknoteDetailsRepository.save(transactionBanknoteDetails);
    }
}
