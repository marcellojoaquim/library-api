package com.books.libraryapi.service.impl;

import com.books.libraryapi.exception.BusinessException;
import com.books.libraryapi.model.entity.Book;
import com.books.libraryapi.model.repository.BookRepository;
import com.books.libraryapi.service.BoookService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BoookService {

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
}
