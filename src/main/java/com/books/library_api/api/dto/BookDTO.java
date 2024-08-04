package com.books.library_api.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {

    private Long id;
    private String title;
    private String author;
    private String isbn;

}
