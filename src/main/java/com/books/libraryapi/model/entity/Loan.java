package com.books.libraryapi.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String customer;
    private Book book;
    private LocalDate loanDate;
    private Boolean returned;


}
