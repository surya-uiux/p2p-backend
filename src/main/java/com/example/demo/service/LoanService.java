package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Loan;
import com.example.demo.model.User;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    public Loan createLoan(Long borrowerId, Double amount) {

        User borrower = userRepository.findById(borrowerId)
                .orElseThrow(() -> new RuntimeException("Borrower not found"));

        if (!borrower.getRole().equals("BORROWER")) {
            throw new RuntimeException("Only borrower can request loan");
        }

        Loan loan = new Loan();
        loan.setBorrower(borrower);
        loan.setAmount(amount);
        loan.setStatus("REQUESTED");
        loan.setCreatedDate(LocalDate.now());

        return loanRepository.save(loan);
    }

    public Loan fundLoan(Long loanId, Long lenderId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (!loan.getStatus().equals("REQUESTED")) {
            throw new RuntimeException("Loan already funded");
        }

        User lender = userRepository.findById(lenderId)
                .orElseThrow(() -> new RuntimeException("Lender not found"));

        if (!lender.getRole().equals("LENDER")) {
            throw new RuntimeException("Only lender can fund");
        }

        loan.setLender(lender);
        loan.setStatus("FUNDED");

        return loanRepository.save(loan);
    }

    public Loan repayLoan(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (!loan.getStatus().equals("FUNDED")) {
            throw new RuntimeException("Loan is not active");
        }

        loan.setStatus("REPAID");

        return loanRepository.save(loan);
    }

    // 🔥 USER-SPECIFIC METHODS

    public List<Loan> getLoansByBorrower(Long userId) {
        User borrower = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return loanRepository.findByBorrower(borrower);
    }

    public List<Loan> getLoansByLender(Long userId) {
        User lender = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return loanRepository.findByLender(lender);
    }
}








