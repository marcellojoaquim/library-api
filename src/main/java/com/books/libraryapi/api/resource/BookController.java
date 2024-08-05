package com.books.libraryapi.api.resource;

import com.books.libraryapi.api.dto.BookDTO;
import com.books.libraryapi.model.entity.Book;
import com.books.libraryapi.service.BoookService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BoookService service;
    ModelMapper modelMapper;

    public BookController(BoookService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody BookDTO dto){
        Book entity = modelMapper.map(dto, Book.class);
        entity = service.save(entity);
        return modelMapper.map(entity, BookDTO.class);
    }
}
