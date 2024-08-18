package com.books.libraryapi.service.impl;

import com.books.libraryapi.exception.BusinessException;
import com.books.libraryapi.model.entity.Book;
import com.books.libraryapi.model.repository.BookRepository;
import com.books.libraryapi.service.BookService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        if(repository.existsByIsbn(book.getIsbn())){
            throw new BusinessException("Isbn already exists.");
        }
        return repository.save(book);
    }

    @Override
    public Optional<Book> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Book book) {
        if(book ==null || book.getId() == null){
            throw new IllegalArgumentException("Book or id can be null");
        }
        repository.delete(book);

    }

    @Override
    public Book update(Book book) {
        if(book ==null || book.getId() == null){
            throw new IllegalArgumentException("Book or id can be null");
        }
        return repository.save(book);
    }

    @Override
    public Page<Book> find(Book filter, Pageable pageable) {
        Example<Book> example = Example.of(filter,
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withIgnoreNullValues()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return repository.findAll(example, pageable);
    }

    @Override
    public Optional<Book> getBookByIsbn(String isbn) {
        return repository.findByIsbn(isbn);
    }
}
