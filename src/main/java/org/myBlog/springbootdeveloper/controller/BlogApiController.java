package org.myBlog.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.myBlog.springbootdeveloper.domain.Article;
import org.myBlog.springbootdeveloper.dto.AddArticleRequest;
import org.myBlog.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BlogApiController {
    private BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request){
        Article savedArticle=blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)//created를 응답하고 테이블에 저장된 객체를 반환한다.
                .body(savedArticle);
    }

}
