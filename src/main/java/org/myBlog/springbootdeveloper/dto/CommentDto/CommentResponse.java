package org.myBlog.springbootdeveloper.dto.CommentDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.myBlog.springbootdeveloper.domain.Comment;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class CommentResponse {
    private Long id;
    private String comment;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt = LocalDateTime.now();
    private String nickname;
    private Long userId;
    private Long ArticleId;

    /* Entity -> Dto*/
    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.createdAt = comment.getCreatedDate();
        this.modifiedAt = comment.getModifiedDate();
        this.ArticleId = comment.getArticle().getId();
        this.nickname = comment.getUser().getNickname();
        this.userId = comment.getUser().getId();
    }
}
