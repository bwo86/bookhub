package com.dgsw.bookhub.repository;

import com.dgsw.bookhub.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository  extends JpaRepository<BookEntity, String> {

}