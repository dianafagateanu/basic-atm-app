package com.demo.atm.repository;

import com.demo.atm.domain.Banknote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanknoteRepository extends JpaRepository<Banknote, Long> {
}
