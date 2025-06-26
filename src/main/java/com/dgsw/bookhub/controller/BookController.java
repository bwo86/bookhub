package com.dgsw.bookhub.controller;

import com.dgsw.bookhub.dto.request.BookCreateRequest;
import com.dgsw.bookhub.dto.request.BookEditRequest;
import com.dgsw.bookhub.dto.request.BookLoanRequest;
import com.dgsw.bookhub.dto.response.BookResponse;
import com.dgsw.bookhub.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "도서 관리 API", description = "도서 등록, 대출, 반납, 조회 등을 관리합니다.")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @Operation(summary = "책 등록", description = "새로운 책을 등록합니다.")
    public ResponseEntity<String> addBook(@RequestBody BookCreateRequest request) {
        boolean success = bookService.addBook(request);
        if (success) {
            return ResponseEntity.ok("책이 성공적으로 등록되었습니다.");
        }
        return ResponseEntity.badRequest().body("이미 존재하는 책 ID입니다.");
    }

    @GetMapping
    @Operation(summary = "전체 책 조회", description = "모든 책 정보를 조회합니다.")
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PutMapping
    @Operation(summary = "책 정보 수정", description = "책의 제목, 저자, 대출 여부를 수정합니다.")
    public ResponseEntity<String> editBook(@RequestBody BookEditRequest request) {
        boolean success = bookService.editBook(request);
        if (success) {
            return ResponseEntity.ok("책 정보가 수정되었습니다.");
        }
        return ResponseEntity.badRequest().body("책을 찾을 수 없습니다.");
    }

    @PostMapping("/loan")
    @Operation(summary = "책 대출 및 반납", description = "`loaned` 값에 따라 책을 대출하거나 반납 처리합니다.")
    public ResponseEntity<String> loanBook(@RequestBody BookLoanRequest request) {
        boolean success = bookService.loanBook(request);
        if (success) {
            String action = request.isLoaned() ? "대출" : "반납";
            return ResponseEntity.ok(action + " 처리 완료");
        }
        return ResponseEntity.badRequest().body("대출/반납 처리 실패");
    }

    @GetMapping("/available")
    @Operation(summary = "대출 가능 도서 조회", description = "현재 대출되지 않은 책 목록을 조회합니다.")
    public ResponseEntity<List<BookResponse>> getAvailableBooks() {
        return ResponseEntity.ok(bookService.getAvailableBooks());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "책 삭제", description = "책 ID를 기반으로 책을 삭제합니다.")
    public ResponseEntity<String> deleteBook(@PathVariable long id) {
        boolean success = bookService.deleteBook(id);
        if (success) {
            return ResponseEntity.ok("책이 삭제되었습니다.");
        }
        return ResponseEntity.badRequest().body("책을 찾을 수 없습니다.");
    }
}