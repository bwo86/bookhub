package com.dgsw.bookhub.controller;

import com.dgsw.bookhub.dto.request.BookCreateRequest;
import com.dgsw.bookhub.dto.request.BookEditRequest;
import com.dgsw.bookhub.dto.request.BookLoanRequest;
import com.dgsw.bookhub.dto.response.BookResponse;
import com.dgsw.bookhub.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody BookCreateRequest request) {
        boolean success = bookService.addBook(request);
        if (success) {
            return ResponseEntity.ok("책이 성공적으로 등록되었습니다.");
        }
        return ResponseEntity.badRequest().body("이미 존재하는 책 ID입니다.");
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<BookResponse> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @PutMapping
    public ResponseEntity<String> editBook(@RequestBody BookEditRequest request) {
        boolean success = bookService.editBook(request);
        if (success) {
            return ResponseEntity.ok("책 정보가 수정되었습니다.");
        }
        return ResponseEntity.badRequest().body("책을 찾을 수 없습니다.");
    }

    @PostMapping("/loan")
    public ResponseEntity<String> loanBook(@RequestBody BookLoanRequest request) {
        boolean success = bookService.loanBook(request);
        if (success) {
            String action = request.isLoaned() ? "대출" : "반납";
            return ResponseEntity.ok(action + " 처리 완료");
        }
        return ResponseEntity.badRequest().body("대출/반납 처리 실패");
    }

    @GetMapping("/available")
    public ResponseEntity<List<BookResponse>> getAvailableBooks() {
        List<BookResponse> availableBooks = bookService.getAvailableBooks();
        return ResponseEntity.ok(availableBooks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable String id) {
        boolean success = bookService.deleteBook(id);
        if (success) {
            return ResponseEntity.ok("책이 삭제되었습니다.");
        }
        return ResponseEntity.badRequest().body("책을 찾을 수 없습니다.");
    }
}