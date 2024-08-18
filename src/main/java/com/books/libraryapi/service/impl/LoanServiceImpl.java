package com.books.libraryapi.service.impl;

import com.books.libraryapi.model.entity.Loan;
import com.books.libraryapi.model.repository.LoanRepository;
import com.books.libraryapi.service.LoanService;

public class LoanServiceImpl implements LoanService {

    private LoanRepository repository;

    public LoanServiceImpl(LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Loan save(Loan loan) {
        return repository.save(loan);
    }
}
