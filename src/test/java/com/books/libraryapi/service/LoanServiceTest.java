package com.books.libraryapi.service;

import com.books.libraryapi.api.dto.LoanFilterDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LoanServiceTest {

    private LoanService service;
    private Book book;
    private Loan loan;

    @MockBean
    LoanRepository repository;

    @BeforeEach
    void setUp(){
        this.service = new LoanServiceImpl(repository);
        book = Book.builder().id(1L).build();
        loan = Loan.builder()
                .book(book)
                .loanDate(LocalDate.now())
                .customer("Cliente")
                .id(1L)
                .build();
    }

    @Test
    @DisplayName("Should save a loan")
    void testSaveLoan() {
        Loan savingLoan = Loan.builder()
                .book(book)
                .loanDate(LocalDate.now())
                .customer("Cliente")
                .build();

        when(repository.existsByBookAndNotReturned(book)).thenReturn(false);
        when(repository.save(savingLoan)).thenReturn(loan);

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

    @Test
    @DisplayName("Should return a loan by id")
    void testGetLoanDetails(){
        long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.of(loan));

        Optional<Loan> actual = service.getByID(id);

        assertNotNull(actual);
        assertEquals(actual.get().getId(), 1L);
        assertEquals(actual.get().getCustomer(), loan.getCustomer());
        assertEquals(actual.get().getBook(), loan.getBook());
        assertEquals(actual.get().getLoanDate(), loan.getLoanDate());

        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Should update a loan")
    void testUpdateLoan(){
        loan.setReturned(true);

        when(repository.save(loan)).thenReturn(loan);

        Loan actual = service.update(loan);

        assertTrue(actual.getReturned());

    }

    @Test
    @DisplayName("Should find loans with parameters")
    void findLoanTest(){

        LoanFilterDTO loanFilterDTO = LoanFilterDTO.builder()
                .customer("Cliente")
                .isbn("123").build();

        PageRequest pageable = PageRequest.of(0, 10);
        List<Loan> list = Arrays.asList(loan);

        Page<Loan> page = new PageImpl<Loan>(list, pageable, list.size());
        when(repository.findByBookIsbnOrCustomer(anyString(), anyString(), any(PageRequest.class)))
                .thenReturn(page);

        Page<Loan> result = service.find(loanFilterDTO, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(list, result.getContent());
        assertEquals(0, result.getPageable().getPageNumber());
        assertEquals(10, result.getPageable().getPageSize());
    }

}
