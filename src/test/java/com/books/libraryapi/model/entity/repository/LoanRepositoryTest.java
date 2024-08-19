package com.books.libraryapi.model.entity.repository;

import com.books.libraryapi.model.entity.Book;
import com.books.libraryapi.model.entity.Loan;
import com.books.libraryapi.model.repository.LoanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class LoanRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LoanRepository repository;

    @Test
    @DisplayName("Should verify returned field for a book")
    void testExistsByBookAndNotReturned(){
        Book book = Book.builder().author("Author").title("New Book").isbn("1234").build();
        entityManager.persist(book);

        Loan loan = Loan.builder()
                .book(book)
                .loanDate(LocalDate.now())
                .customer("Cliente")
                .build();
        entityManager.persist(loan);

        boolean exists = repository.existsByBookAndNotReturned(book);
        assertTrue(exists);
    }
}
