package com.dgsw.bookhub.service;

import com.dgsw.bookhub.dto.request.BookCreateRequest;
import com.dgsw.bookhub.dto.request.BookEditRequest;
import com.dgsw.bookhub.dto.request.BookLoanRequest;
import com.dgsw.bookhub.dto.response.BookResponse;
import com.dgsw.bookhub.entity.BookEntity;
import com.dgsw.bookhub.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public boolean addBook(BookCreateRequest request) {
        BookEntity book = BookEntity.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .loaned(false).build();
        bookRepository.save(book);
        return true;
    }

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // 대출/반납 통합 메서드
    public boolean loanBook(BookLoanRequest request) {
        Optional<BookEntity> bookOpt = bookRepository.findById(request.getId());
        if (bookOpt.isEmpty()) return false;

        BookEntity book = bookOpt.get();

        // 대출 시 이미 대출 중이면 실패
        if (request.isLoaned() && book.isLoaned()) return false;

        // 반납 시 이미 반납 상태면 실패
        if (!request.isLoaned() && !book.isLoaned()) return false;

        book.setLoaned(request.isLoaned());
        bookRepository.save(book);
        return true;
    }

    public boolean editBook(BookEditRequest request) {
        Optional<BookEntity> bookOpt = bookRepository.findById(request.getId());
        if (bookOpt.isPresent()) {
            BookEntity book = bookOpt.get();
            book.setTitle(request.getTitle());
            book.setAuthor(request.getAuthor());
            book.setLoaned(request.isLoaned());
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    public List<BookResponse> getAvailableBooks() {
        return bookRepository.findAll().stream()
                .filter(book -> !book.isLoaned())
                .map(this::toResponse)
                .toList();
    }

    public boolean deleteBook(long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private BookResponse toResponse(BookEntity book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.isLoaned()
        );
    }
}