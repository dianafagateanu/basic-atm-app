package com.demo.atm.service;

import com.demo.atm.domain.Banknote;
import com.demo.atm.domain.Currency;
import com.demo.atm.repository.BanknoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BanknoteService {

    private final BanknoteRepository banknoteRepository;

    List <Banknote> findAllBanknotesByCurrency(Currency currency) {
        return banknoteRepository.findAllByCurrency(currency);
    }

    List <Banknote> findAllByCurrencyOrderByBillValueDesc(Currency currency) {
        return banknoteRepository.findAllByCurrencyOrderByBillValueDesc(currency);
    }

    Banknote findByBillValueAndCurrency(Long billValue, Currency currency) {
        return banknoteRepository.findByBillValueAndCurrency(billValue, currency);
    }
}
