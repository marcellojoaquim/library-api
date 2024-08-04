package com.books.library_api.api.resource;

import com.books.library_api.api.dto.BookDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(){
        BookDTO dto = new BookDTO();
        dto.setId(1L);
        dto.setAuthor("Author");
        dto.setTitle("New Book");
        dto.setIsbn("1234");
        return dto;
    }
}
