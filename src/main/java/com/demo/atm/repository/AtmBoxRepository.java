package com.demo.atm.repository;

import com.demo.atm.domain.AtmBox;
import com.demo.atm.domain.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtmBoxRepository extends JpaRepository<AtmBox, Long> {

    Optional<AtmBox> findByBanknote_BillValueAndBanknote_Currency(Long billValue, Currency currency);
}
