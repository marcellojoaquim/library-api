package com.books.libraryapi.model.entity.repository;

import com.books.libraryapi.model.entity.Book;
import com.books.libraryapi.model.entity.Loan;
import com.books.libraryapi.model.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class LoanRepositoryTest {

    private Book book;
    private Loan loan;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LoanRepository repository;

    @BeforeEach
    void setup(){
        book = Book.builder().author("Author").title("New Book").isbn("1234").build();
        entityManager.persist(book);

        loan = Loan.builder()
                .book(book)
                .loanDate(LocalDate.now())
                .customer("Cliente")
                .build();
        entityManager.persist(loan);
    }

    @Test
    @DisplayName("Should verify returned field for a book is true")
    void testExistsByBookAndNotReturned(){

        boolean exists = repository.existsByBookAndNotReturned(book);
        assertTrue(exists);
    }

    @Test
    @DisplayName("should find an page of loans by isbn or customer")
    void testFindByBookOrCustomer(){
        Page<Loan> actual = repository.findByBookIsbnOrCustomer("123", "Cliente", PageRequest.of(0, 10));

        assertEquals(10, actual.getSize());
        assertEquals(10, actual.getPageable().getPageSize());
        assertEquals(0, actual.getPageable().getPageNumber());
        assertEquals(1, actual.getTotalElements());

    }
}
