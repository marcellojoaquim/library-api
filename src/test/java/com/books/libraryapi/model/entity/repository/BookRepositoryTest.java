package com.books.libraryapi.model.entity.repository;

import com.books.libraryapi.model.entity.Book;
import com.books.libraryapi.model.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
class BookRepositoryTest {

    private Book book;

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookRepository repository;

    @BeforeEach
    void setup(){
        book = Book.builder().author("Author").title("New Book").isbn("123").build();
    }

    @Test
    @DisplayName("Should return true when find a book by isbn")
    void testExistsByIsbn() {

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

    @Test
    @DisplayName("Should found a book by id")
    void testFindBookById(){
        entityManager.persist(book);

        Optional<Book> foundBook = repository.findById(book.getId());

        assertNotNull(foundBook);
    }

    @Test
    @DisplayName("Should save a book")
    void testSaveBook(){
        Book savedBook = repository.save(book);

        assertNotNull(savedBook.getId());
        assertEquals("Author", savedBook.getAuthor());
    }

    @Test
    @DisplayName("Should delete a book")
    void testDeleteBook(){
        entityManager.persist(book);
        Book foundBook = entityManager.find(Book.class, book.getId());

        repository.delete(foundBook);

        Book deletedBook = entityManager.find(Book.class, book.getId());

        assertNull(deletedBook);
    }
}