package com.demo.atm.controller;

import com.demo.atm.domain.Account;
import com.demo.atm.domain.TransactionType;
import com.demo.atm.exception.InsufficientFundsException;
import com.demo.atm.model.DepositBanknoteModel;
import com.demo.atm.model.WithdrawModel;
import com.demo.atm.service.AccountService;
import com.demo.atm.service.DepositService;
import com.demo.atm.service.WithdrawService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    private final WithdrawService withdrawService;

    @GetMapping
    public String getOperationTypeView(Model model, Principal principal) {
        List <TransactionType> operationTypeList = Arrays.asList(TransactionType.values());
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
    public String depositAmount(@ModelAttribute("depositBanknoteModel") DepositBanknoteModel depositBanknoteModel,
                                Model model, Principal principal) {
        Long amountDeposited = depositService.processDepositOperation(depositBanknoteModel, accountService.getAccountByUsername(principal.getName()));
        model.addAttribute("amountDeposited", amountDeposited);
        model.addAttribute("account", accountService.getAccountModelForClient(principal.getName()));
        return "depositFormSuccess";
    }

    @GetMapping("/withdraw")
    public String getAccountWithdrawView(Model model, Principal principal) {
        model.addAttribute("withdrawModel", new WithdrawModel());
        model.addAttribute("account", accountService.getAccountModelForClient(principal.getName()));
        return "withdrawForm";
    }

    //TODO handle exception if the amount introduced is not multiple of 10
    //TODO handle exception if the amount requested is not available in the ATM/each ATM Box -> insufficient ATM funds
    //TODO update WithdrawModel with currency in order to be able to withdraw money in multiple currencies (EUR from RON account or RON from EUR account)
    //TODO handle exchange rates
    @PostMapping("/withdraw")
    public String withdrawAmount(@ModelAttribute("withdrawModel") WithdrawModel withdrawModel,
                                 Model model, Principal principal) {
        Account account = accountService.getAccountByUsername(principal.getName());
        if (withdrawModel.getAmountWithdrawn() <= account.getBalance()) {
            withdrawService.processWithdrawOperation(withdrawModel, accountService.getAccountByUsername(principal.getName()));
            model.addAttribute("account", accountService.getAccountModelForClient(principal.getName()));
            return "withdrawFormSuccess";
        }
        throw new InsufficientFundsException("Insufficient funds. Please try again!");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InsufficientFundsException.class)
    public ModelAndView handleInsufficientFundsException(Exception exception, Principal principal) {
        log.error("Handling insufficient funds exception for user {} ", principal.getName());
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("withdrawFormInsufficientFunds");
        modelAndView.addObject("exceptionMessage", exception.getMessage());
        modelAndView.addObject("withdrawModel", new WithdrawModel());
        modelAndView.addObject("account", accountService.getAccountModelForClient(principal.getName()));

        return modelAndView;
    }

}
