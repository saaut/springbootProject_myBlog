package org.myBlog.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.myBlog.springbootdeveloper.dto.CommentDto;
import org.myBlog.springbootdeveloper.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * REST API Controller
 */
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentApiController {

    private final CommentService commentService;

    /* CREATE */
    @PostMapping("/articles/{id}/comments")
    public ResponseEntity<Long> save(@PathVariable long id, @RequestBody CommentDto.Request dto,
                                     Principal principal) {
        return ResponseEntity.ok(commentService
                .save(id, principal.getName(), dto));
    }

    /* READ */
    @GetMapping("/articles/{id}/comments")
    public List<CommentDto.Response> read(@PathVariable long id) {
        return commentService.findAll(id);
    }

    /* UPDATE */
    @PutMapping({"/articles/{articleId}/comments/{id}"})
    public ResponseEntity<Long> update(@PathVariable long postsId, @PathVariable Long id, @RequestBody CommentDto.Request dto) {
        commentService.update(postsId, id, dto);
        return ResponseEntity.ok(id);
    }

    /* DELETE */
    @DeleteMapping("/articles/{articleId}/comments/{id}")
    public ResponseEntity<Long> delete(@PathVariable long postsId, @PathVariable Long id) {
        commentService.delete(postsId, id);
        return ResponseEntity.ok(id);
    }
}
