package com.books.libraryapi.service;

import com.books.libraryapi.api.dto.LoanFilterDTO;
import com.books.libraryapi.model.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LoanService {

    Loan save(Loan loan);

    Optional<Loan> getByID(Long id);

    Loan update(Loan loan);

    Page<Loan> find(LoanFilterDTO loanFilterDTO, Pageable pageable);
}
