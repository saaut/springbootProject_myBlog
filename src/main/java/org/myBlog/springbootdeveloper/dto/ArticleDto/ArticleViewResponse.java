package org.myBlog.springbootdeveloper.dto.ArticleDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myBlog.springbootdeveloper.domain.Article;
import org.myBlog.springbootdeveloper.dto.CommentDto.CommentResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ArticleViewResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String author;

    private List<CommentResponse> comments;

    public ArticleViewResponse(Article article){
        this.id= article.getId();
        this.title=article.getTitle();
        this.content= article.getContent();
        this.createdAt = LocalDateTime.now();
        this.author=article.getAuthor();
        this.comments = article.getComments().stream().map(CommentResponse::new).collect(Collectors.toList());
    }
}
