package com.dgsw.bookhub.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookCreateRequest {
    private String id;
    private String title;
    private String author;
}