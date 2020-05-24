package com.demo.atm;

import com.demo.atm.domain.*;
import com.demo.atm.model.AccountModel;
import com.demo.atm.model.CardDetailsModel;

import java.util.Arrays;
import java.util.List;

public class SamplesGenerator {

    //TODO replace with builders

    public static Currency getCurrencySample() {
        Currency currency = new Currency();
        currency.setId(1L);
        currency.setName("ron");
        return currency;
    }

    public static Account getAccountSample(Currency currency) {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(10000L);
        account.setCurrency(currency);
        return account;
    }

    public static Transaction getTransactionSample(TransactionType transactionType, Long amount,
                                                   Currency currency, Account account) {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setTransactionType(transactionType);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setAccount(account);
        return transaction;
    }

    public static List <Banknote> getSortedBanknoteListSample(Currency currency) {
        Banknote banknote10 = new Banknote();
        banknote10.setId(1L);
        banknote10.setBillValue(10L);
        banknote10.setCurrency(currency);

        Banknote banknote50 = new Banknote();
        banknote50.setId(2L);
        banknote50.setBillValue(50L);
        banknote50.setCurrency(currency);

        Banknote banknote100 = new Banknote();
        banknote100.setId(3L);
        banknote100.setBillValue(100L);
        banknote100.setCurrency(currency);

        return Arrays.asList(banknote100, banknote50, banknote10);
    }

    public static CardDetailsModel getCardDetailsModelSample() {
        CardDetailsModel cardDetailsModel = new CardDetailsModel();
        cardDetailsModel.setCardNumber("4463289457379346");
        cardDetailsModel.setPinNumber("1212");
        return cardDetailsModel;
    }
}
