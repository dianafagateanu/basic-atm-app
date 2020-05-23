package com.demo.atm.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class AtmBox {

    @Id
    private Long id;

    private Long countBills;

    @OneToOne
    @JoinColumn(name="banknote_id", referencedColumnName = "id")
    private Banknote banknote;

    @Embedded
    private Audit audit = new Audit();
}
