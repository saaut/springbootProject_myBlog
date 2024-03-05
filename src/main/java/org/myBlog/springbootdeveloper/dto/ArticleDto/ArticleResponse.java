package org.myBlog.springbootdeveloper.dto.ArticleDto;

import lombok.Getter;
import org.myBlog.springbootdeveloper.domain.Article;

@Getter
public class ArticleResponse {
    //클래스 생성
    private final String title;
    private final String content;

    public ArticleResponse(Article article){//엔티티를 인수로 받는 생성자
        this.title=article.getTitle();
        this.content=article.getContent();
    }
}
