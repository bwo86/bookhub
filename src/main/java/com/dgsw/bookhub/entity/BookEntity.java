package com.dgsw.bookhub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "books")
public class BookEntity {
    @Id
    private String id;
    private String title;
    private String author;
    private boolean loaned;
}