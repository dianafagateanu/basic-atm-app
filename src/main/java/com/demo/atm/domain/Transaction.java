package com.demo.atm.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;

    private Long amount;

    @OneToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @OneToMany(mappedBy = "transaction")
    private Set<TransactionBanknoteDetails> transactionBanknoteDetailsSet = new HashSet <>();

    @ManyToOne
    private Account account;

    @Embedded
    private Audit audit = new Audit();
}
