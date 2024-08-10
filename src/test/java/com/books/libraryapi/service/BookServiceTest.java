package com.books.libraryapi.service;

import com.books.libraryapi.exception.BusinessException;
import com.books.libraryapi.model.entity.Book;
import com.books.libraryapi.model.repository.BookRepository;
import com.books.libraryapi.service.impl.BookServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class BookServiceTest {

    BoookService service;

    @MockBean
    BookRepository repository;

    @BeforeEach
    void setup() {
        this.service = new BookServiceImpl(repository);
    }

    @Test
    @DisplayName("Should return success when save book")
    void testSaveBook() {
        Book book = Book.builder().author("Author").title("New Book").isbn("1234").build();

        when(repository.existsByIsbn(anyString())).thenReturn(false);
        when(repository.save(book)).thenReturn(Book.builder().id(1L).isbn("1234").title("New Book").author("Author").build());

        Book savedBook = service.save(book);

        assertNotNull(savedBook.getId());
        assertEquals("1234", savedBook.getIsbn());
        assertEquals("New Book", savedBook.getTitle());
        assertEquals("Author", savedBook.getAuthor());
    }

    @Test
    @DisplayName("Should throws an Exception when save a book with isbn existent")
    void testSaveBookWithIsbnExistent() {
        Book book = Book.builder().author("Author").title("New Book").isbn("1234").build();
        when(repository.existsByIsbn(anyString())).thenReturn(true);

        Throwable exception = Assertions.catchThrowable(() -> service.save(book));
        assertThat(exception).isInstanceOf(BusinessException.class)
                .hasMessage("Isbn already exists.");

        verify(repository, never()).save(book);
    }

    @Test
    @DisplayName("Should return a book by id")
    void testFindBookById() {
        Long id = 1L;
        Book book = Book.builder().author("Author").title("New Book").isbn("1234").build();
        book.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = service.getById(id);

        assertNotNull(book.getId());
        assertEquals(1L, book.getId());
        assertEquals("Author", book.getAuthor());
        assertEquals("New Book", book.getTitle());
        assertEquals("1234", book.getIsbn());
    }

    @Test
    @DisplayName("Should return empty when nonexistent book")
    void testBookNotFoundByID() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<Book> foundBook = service.getById(id);

        //assertNull(foundBook);
        assertThat(foundBook.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Should delete a book")
    void testDeleteBookById() {
        Book book = Book.builder().id(1L).author("Author").title("New Book").isbn("1234").build();
        BDDMockito.willDoNothing().given(repository).delete(book);

        assertDoesNotThrow(() -> service.delete(book));

        verify(repository, times(1)).delete(book);

    }

    @Test
    @DisplayName("Should throws an illegal argument exception when delete with nullable book or id")
    void testDeleteNonExistentBook(){
        var expectedMessage = "Book or id can be null";
        Book book = null;

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> {service.delete(book);},
                () -> "Should Throws an Exception");

        assertEquals(expectedMessage, ex.getMessage());
        verify(repository, never()).delete(book);
    }

    @Test
    @DisplayName("Should return a updated book")
    void testUpdatedBook(){
        Book book = Book.builder().id(1L).author("Author").title("New Book").isbn("1234").build();
        when(repository.save(book)).thenReturn(book);

        Book updatedBook = service.update(book);

        assertNotNull(updatedBook.getId());
        assertEquals(book.getTitle(), updatedBook.getTitle());
        assertEquals(book.getAuthor(), updatedBook.getAuthor());
        assertEquals(book.getIsbn(), updatedBook.getIsbn());
    }

    @Test
    @DisplayName("Should throws an illegal argument exception when update with nullable book or id")
    void testUpdateNonexistentBook(){
        var expectedMessage = "Book or id can be null";
        Book book = null;

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> {service.update(book);},
                () -> "Should Throws an Exception");

        assertEquals(expectedMessage, ex.getMessage());
        verify(repository, never()).save(book);
    }
}