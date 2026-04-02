package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Loan;
import com.example.demo.repository.LoanRepository;
import com.example.demo.service.LoanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
@CrossOrigin
public class LoanController {

    private final LoanService loanService;
    private final LoanRepository loanRepository; // 🔥 ADD THIS

    @GetMapping("/create")
    public Loan createLoan(@RequestParam Long borrowerId,
                           @RequestParam Double amount) {
        return loanService.createLoan(borrowerId, amount);
    }

    @GetMapping("/fund")
    public Loan fundLoan(@RequestParam Long loanId,
                         @RequestParam Long lenderId) {
        return loanService.fundLoan(loanId, lenderId);
    }

    @GetMapping("/repay")
    public Loan repayLoan(@RequestParam Long loanId) {
        return loanService.repayLoan(loanId);
    }

    // 🔥 IMPORTANT ENDPOINT (THIS FIXES YOUR ERROR)
    @GetMapping("/all")
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
  

}















