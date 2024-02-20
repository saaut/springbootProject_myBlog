package org.myBlog.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myBlog.springbootdeveloper.domain.Article;

import java.time.LocalDateTime;
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

    private List<CommentDto.Response> comments;

    public ArticleViewResponse(Article article){
        this.id= article.getId();
        this.title=article.getTitle();
        this.content= article.getContent();
        this.createdAt=article.getCreatedAt();
        this.author=article.getAuthor();
        this.comments = article.getComments().stream().map(CommentDto.Response::new).collect(Collectors.toList());

    }
}
