package com.demo.atm.controller;

import com.demo.atm.domain.Account;
import com.demo.atm.exception.InvalidCardDetailsException;
import com.demo.atm.model.CardDetailsModel;
import com.demo.atm.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Slf4j
@Controller
@RequiredArgsConstructor
public class CardController {

    private final AccountService accountService;

    @GetMapping("/")
    public String getCardDetailsPage(Model model) {
        model.addAttribute("cardDetails", new CardDetailsModel());
        return "cardDetailsForm";
    }

    @PostMapping("/checkCardDetails")
    public String checkCardDetails(@ModelAttribute("cardDetails") CardDetailsModel cardDetailsDto) {
        Optional<Account> optionalAccount = accountService.findAccountByCardDetails(cardDetailsDto);
        if (optionalAccount.isPresent()) {
            log.info("Valid card details");
            return "redirect:" + "/operationType";
        }
        throw new InvalidCardDetailsException("Card details are incorrect. Please try again!");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InvalidCardDetailsException.class)
    public ModelAndView handleInvalidCardDetailsException(Exception exception) {
        log.error("Handling invalid card details exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("invalidCardDetailsForm");
        modelAndView.addObject("exceptionMessage", exception.getMessage());
        modelAndView.addObject("cardDetails", new CardDetailsModel());

        return modelAndView;
    }
}
