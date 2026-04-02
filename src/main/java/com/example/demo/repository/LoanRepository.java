package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Loan;
import com.example.demo.model.User;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByBorrower(User borrower);

    List<Loan> findByLender(User lender);
}



