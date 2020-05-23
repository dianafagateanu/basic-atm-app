package com.demo.atm.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class TransactionBanknoteDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long countBills;

    @OneToOne
    @JoinColumn(name="banknote_id", referencedColumnName = "id")
    private Banknote banknote;

    @ManyToOne
    private Transaction transaction;

    @Embedded
    private Audit audit = new Audit();
}
