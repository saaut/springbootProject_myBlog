package org.myBlog.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.myBlog.springbootdeveloper.domain.Article;
import org.myBlog.springbootdeveloper.dto.AddArticleRequest;
import org.myBlog.springbootdeveloper.dto.ArticleResponse;
import org.myBlog.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogApiController {
    private final BlogService blogService;
    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)//created를 응답하고 테이블에 저장된 객체를 반환한다.
                .body(savedArticle);
    }
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> articles=blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id){
        Article article = blogService.findById(id);

        return ResponseEntity.ok()//글을 찾으면
                .body(new ArticleResponse(article));//3번 글의 정보를 body에 담아 웹 브라우저로 전송한다.
    }

}
