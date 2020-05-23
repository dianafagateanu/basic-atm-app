package com.demo.atm.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Banknote {

    @Id
    private Long id;

    private Long billValue;

    @OneToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @Embedded
    private Audit audit = new Audit();
}
