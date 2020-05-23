package com.demo.atm.repository;

import com.demo.atm.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository <Account, Long> {

    Optional <Account> findByCardNumberAndPinNumber(String cardNumber, String pinNumber);

    Optional <Account> findByUsername(String username);
}
