package com.demo.atm.domain;

public enum TransactionType {

    DEPOSIT, WITHDRAW, BALANCE;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
