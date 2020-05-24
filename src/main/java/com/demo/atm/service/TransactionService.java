package com.demo.atm.service;

import com.demo.atm.domain.*;
import com.demo.atm.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TransactionService {

    //TODO cascading for TransactionBankNoteDetails and replace with builder pattern

    private final TransactionRepository transactionRepository;

    Transaction createTransaction(TransactionType transactionType, Long amount, Account account) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionType);
        transaction.setAmount(amount);
        transaction.setCurrency(account.getCurrency());
        transaction.setAccount(account);
        return transactionRepository.save(transaction);
    }
}
