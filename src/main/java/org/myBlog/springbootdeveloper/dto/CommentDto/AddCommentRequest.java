package org.myBlog.springbootdeveloper.dto.CommentDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myBlog.springbootdeveloper.domain.Article;
import org.myBlog.springbootdeveloper.domain.Comment;
import org.myBlog.springbootdeveloper.domain.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCommentRequest {
    private Long id;
    private String comment;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime modifiedDate = LocalDateTime.now();
    private User user;
    private Article article;

    public Comment toEntity() {//생성자를 사용해 객체 생성
        return Comment.builder()
                .comment(comment)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .article(article)
                .user(user)
                .build();
    }
}
