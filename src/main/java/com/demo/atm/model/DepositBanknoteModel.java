package com.demo.atm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositBanknoteModel {

    private Map<Long, Long> depositBanknoteMap;
}
