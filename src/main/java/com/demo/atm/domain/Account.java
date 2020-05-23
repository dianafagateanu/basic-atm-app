package com.demo.atm.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Account {

    //TODO validations @NonNull, @NonBlank, length for iban, card number etc for each field in all domain entities according to business request

    @Id
    private Long id;

    private String iban;

    private String accountName;

    private String username;

    private String fullName;

    private String cardNumber;

    private String pinNumber;

    private Long balance;

    private String bank;

    private String swift;

    private String bic;

    @OneToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @OneToMany(mappedBy = "account")
    private Set<Transaction> transactions = new HashSet <>();

    @Embedded
    private Audit audit = new Audit();
}
