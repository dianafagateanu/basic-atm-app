package com.demo.atm.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountModel {

    private String accountName;

    private String currency;

    private Long balance;
}
