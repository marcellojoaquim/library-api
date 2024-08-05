package com.books.libraryapi.service.impl;

import com.books.libraryapi.model.entity.Book;
import com.books.libraryapi.model.repository.BookRepository;
import com.books.libraryapi.service.BoookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BoookService {

    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        return repository.save(book);
    }
}
