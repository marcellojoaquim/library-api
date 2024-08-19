package com.books.libraryapi.service.impl;

import com.books.libraryapi.exception.BusinessException;
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
        if(repository.existsByBookAndNotReturned(loan.getBook())){
            throw new BusinessException("Book already loaned");
        }
        return repository.save(loan);
    }
}
