package com.demo.atm.repository;

import com.demo.atm.domain.AtmBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtmBoxRepository extends JpaRepository<AtmBox, Long> {
}
