package com.demo.atm.service;

import com.demo.atm.domain.AtmBox;
import com.demo.atm.domain.Currency;
import com.demo.atm.domain.TransactionType;
import com.demo.atm.model.DepositBanknoteModel;
import com.demo.atm.repository.AtmBoxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static com.demo.atm.utils.Constants.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AtmBoxService {

    private final AtmBoxRepository atmBoxRepository;

    public void updateAtmBoxes(DepositBanknoteModel depositBanknoteModel, Currency currency, TransactionType transactionType) {
        depositBanknoteModel.getDepositBanknoteMap().forEach((depositBanknoteKey, depositBanknoteValue) -> {
            AtmBox atmBox = atmBoxRepository.findByBanknote_BillValueAndBanknote_Currency(depositBanknoteKey, currency)
                    .orElseThrow(() -> new EntityNotFoundException(ATM_BOX_EXCEPTION_MESSAGE_ERROR + depositBanknoteKey));
            updateAtmBox(atmBox, depositBanknoteValue, transactionType);
        });

    }

    private void updateAtmBox(AtmBox atmBox, Long countOperationBills, TransactionType transactionType) {
        if (TransactionType.DEPOSIT.equals(transactionType)) {
            atmBox.setCountBills(atmBox.getCountBills() + countOperationBills);
        } else if (TransactionType.WITHDRAW.equals(transactionType)) {
            //TODO handle exceptions in case the number of bills withdrawn is larger than the available number of bills from the given atmBox
            atmBox.setCountBills(atmBox.getCountBills() - countOperationBills);
        }
        atmBoxRepository.save(atmBox);
    }
}
