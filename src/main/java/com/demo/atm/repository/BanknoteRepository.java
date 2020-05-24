package com.demo.atm.repository;

import com.demo.atm.domain.Banknote;
import com.demo.atm.domain.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BanknoteRepository extends JpaRepository<Banknote, Long> {

    List<Banknote> findAllByCurrency(Currency currency);

    Banknote findByBillValueAndCurrency(Long billValue, Currency currency);
}
