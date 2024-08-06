package com.books.libraryapi.model.entity.repository;

import com.books.libraryapi.model.entity.Book;
import com.books.libraryapi.model.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookRepository repository;

    @Test
    @DisplayName("Should return true when find a book by isbn")
    void testExistsByIsbn() {

        Book book = Book.builder().author("Author").title("New Book").isbn("123").build();
        entityManager.persist(book);
        String isbn = "123";
        boolean exists = repository.existsByIsbn(isbn);

        assertTrue(exists);
    }

    @Test
    @DisplayName("Should return false when find a book by isbn")
    void testNotExistsByIsbn() {

        String isbn = "123";

        boolean exists = repository.existsByIsbn(isbn);

        assertFalse(exists);
    }
}