package com.dgsw.bookhub.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookEditRequest {
    private String id;
    private String title;
    private String author;
    private boolean loaned;
}