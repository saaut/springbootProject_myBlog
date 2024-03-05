package org.myBlog.springbootdeveloper.dto.CommentDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.myBlog.springbootdeveloper.domain.Article;
import org.myBlog.springbootdeveloper.domain.Comment;
import org.myBlog.springbootdeveloper.domain.User;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Getter
public class CommentResponse {
    private Long id;
    private String comment;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime modifiedDate = LocalDateTime.now();
    private String nickname;
    private Long userId;
    private Long ArticleId;

    /* Entity -> Dto*/
    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
        this.ArticleId = comment.getArticle().getId();
        this.nickname = comment.getUser().getNickname();
        this.userId = comment.getUser().getId();
    }
}
