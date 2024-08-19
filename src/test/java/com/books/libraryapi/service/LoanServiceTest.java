package com.books.libraryapi.service;

import com.books.libraryapi.exception.BusinessException;
import com.books.libraryapi.model.entity.Book;
import com.books.libraryapi.model.entity.Loan;
import com.books.libraryapi.model.repository.LoanRepository;
import com.books.libraryapi.service.impl.LoanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LoanServiceTest {

    LoanService service;

    @MockBean
    LoanRepository repository;

    @BeforeEach
    void setUp(){
        this.service = new LoanServiceImpl(repository);
    }

    @Test
    @DisplayName("Should save a loan")
    void testSaveLoan() {
        Book book = Book.builder().id(1L).build();
        Loan savingLoan = Loan.builder()
                .book(book)
                .loanDate(LocalDate.now())
                .customer("Cliente")
                .build();

        Loan savedLoan = Loan.builder()
                        .id(1L)
                        .customer("Cliente")
                        .book(book)
                        .loanDate(LocalDate.now())
                        .build();

        when(repository.existsByBookAndNotReturned(book)).thenReturn(false);
        when(repository.save(savingLoan)).thenReturn(savedLoan);

        Loan loan = service.save(savingLoan);

        assertNotNull(loan);
        assertEquals(1L, loan.getId());
        assertEquals(1L, loan.getBook().getId());
        assertEquals("Cliente", loan.getCustomer());

        verify(repository, times(1)).save(savingLoan);

    }

    @Test
    @DisplayName("Should throw an exception when create a new loan for loaned book")
    void testThrowExceptionLoanedBook() {
        Book book = Book.builder().id(1L).build();
        Loan savingLoan = Loan.builder()
                .book(book)
                .loanDate(LocalDate.now())
                .customer("Cliente")
                .build();

        when(repository.existsByBookAndNotReturned(book)).thenReturn(true);

        Throwable ex = catchThrowable(() -> service.save(savingLoan));
        assertThat(ex).isInstanceOf(BusinessException.class)
                .hasMessage("Book already loaned");

        verify(repository, never()).save(savingLoan);
    }

}
