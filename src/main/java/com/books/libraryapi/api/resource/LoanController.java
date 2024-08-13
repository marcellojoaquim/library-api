package com.books.libraryapi.api.resource;

import com.books.libraryapi.api.dto.LoanDTO;
import com.books.libraryapi.model.entity.Book;
import com.books.libraryapi.model.entity.Loan;
import com.books.libraryapi.service.BookService;
import com.books.libraryapi.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService service;
    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody LoanDTO dto){
        Book book = bookService.getBookByIsbn(dto.getIsbn()).get();

        Loan entity = Loan.builder()
                .book(book)
                .customer(dto.getCustomer())
                .loanDate(LocalDate.now())
                .build();

        entity = service.save(entity);

        return entity.getId();
    }
}
