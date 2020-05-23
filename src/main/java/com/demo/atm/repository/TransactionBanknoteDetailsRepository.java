package com.demo.atm.repository;

import com.demo.atm.domain.TransactionBanknoteDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionBanknoteDetailsRepository extends JpaRepository<TransactionBanknoteDetails, Long> {
}
