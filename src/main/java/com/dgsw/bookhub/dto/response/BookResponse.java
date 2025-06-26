package com.dgsw.bookhub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookResponse {
    private long id;
    private String title;
    private String author;
    private boolean loaned;
}
