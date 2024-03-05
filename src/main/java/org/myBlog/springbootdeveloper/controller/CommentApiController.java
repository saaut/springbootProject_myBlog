package org.myBlog.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.myBlog.springbootdeveloper.domain.Comment;
import org.myBlog.springbootdeveloper.dto.CommentDto.AddCommentRequest;
import org.myBlog.springbootdeveloper.dto.CommentDto.CommentResponse;
import org.myBlog.springbootdeveloper.dto.CommentDto.UpdateCommentRequest;
import org.myBlog.springbootdeveloper.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentApiController {
    private final CommentService commentService;
    //댓글 생성
    @PostMapping("/articles/{id}/comments") //+현재 인증 정보를 가져오는 principal객체
    public ResponseEntity<Comment> save(@PathVariable Long id,@RequestBody AddCommentRequest request,Principal principal) {
        Comment savedComment=commentService.save(id,request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedComment);
    }

    //댓글 읽어오기
    @GetMapping("/articles/{id}/comments")
    public List<Comment> read(@PathVariable long id) {
        return commentService.findAll(id);
    }

    //댓글 업데이트
    @PutMapping({"/articles/{articleId}/comments/{id}"})
    public ResponseEntity<Long> update(@PathVariable long articleId, @PathVariable Long id, @RequestBody UpdateCommentRequest dto) {
        commentService.update(articleId, id, dto);
        return ResponseEntity.ok(id);
    }

    //댓글 삭제
    @DeleteMapping("/articles/{articleId}/comments/{id}")
    public ResponseEntity<Long> delete(@PathVariable long articleId, @PathVariable Long id) {
        commentService.delete(articleId, id);
        return ResponseEntity.ok(id);
    }
}
