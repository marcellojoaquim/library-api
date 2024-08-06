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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class BoookServiceTest {

    BoookService service;

    @MockBean
    BookRepository repository;

    @BeforeEach
    void setup(){
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
    void testSaveBookWithIsbnExistent(){
        Book book = Book.builder().author("Author").title("New Book").isbn("1234").build();
        when(repository.existsByIsbn(anyString())).thenReturn(true);

        Throwable exception = Assertions.catchThrowable(() -> service.save(book));
        assertThat(exception).isInstanceOf(BusinessException.class)
                .hasMessage("Isbn already exists.");

        verify(repository, never()).save(book);
    }
}