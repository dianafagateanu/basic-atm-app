package com.demo.atm.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Currency {

    @Id
    private Long id;

    private String name;

    @Embedded
    private Audit audit = new Audit();
}
