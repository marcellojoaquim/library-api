package com.books.libraryapi.service;

import com.books.libraryapi.model.entity.Book;

import java.util.Optional;

public interface BoookService {
    Book save(Book any);

    Optional<Book> getById(Long id);

    void delete(Book book);

    Book update(Book book);
}
