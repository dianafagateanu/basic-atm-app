package com.demo.atm.controller;

import com.demo.atm.domain.TransactionType;
import com.demo.atm.model.DepositBanknoteModel;
import com.demo.atm.service.AccountService;
import com.demo.atm.service.DepositService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/operationType")
public class OperationController {

    private final AccountService accountService;
    private final DepositService depositService;

    @GetMapping
    public String getOperationTypeView(Model model, Principal principal) {
        List<TransactionType> operationTypeList = Arrays.asList(TransactionType.values());
        model.addAttribute("operationTypes", operationTypeList);
        model.addAttribute("username", accountService.getAccountClientFullName(principal.getName()));
        return "operationTypes";
    }

    @GetMapping("/balance")
    public String getAccountBalanceView(Model model, Principal principal) {
        model.addAttribute("account", accountService.getAccountModelForClient(principal.getName()));
        return "accountBalance";
    }

    @GetMapping("/deposit")
    public String getAccountDepositView(Model model, Principal principal) {
        model.addAttribute("account", accountService.getAccountModelForClient(principal.getName()));
        model.addAttribute("depositBanknote", new DepositBanknoteModel(depositService.getBanknotesTypeForClientAccountCurrency(principal.getName())));
        return "depositForm";
    }

    @PostMapping("/deposit")
    public String depositAmount(@ModelAttribute("depositBanknoteModel") DepositBanknoteModel depositBanknoteModel, Model model, Principal principal) {
        Long amountDeposited = depositService.processDepositOperation(depositBanknoteModel, accountService.getAccountByUsername(principal.getName()));
        model.addAttribute("amountDeposited", amountDeposited);
        model.addAttribute("account", accountService.getAccountModelForClient(principal.getName()));
        return "depositFormSuccess";
    }

}
