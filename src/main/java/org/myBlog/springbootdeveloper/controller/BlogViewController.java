package org.myBlog.springbootdeveloper.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.myBlog.springbootdeveloper.domain.Article;
import org.myBlog.springbootdeveloper.dto.ArticleListViewResponse;
import org.myBlog.springbootdeveloper.dto.ArticleViewResponse;
import org.myBlog.springbootdeveloper.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model){
        List< ArticleListViewResponse> articles = blogService.findAll().stream()
                .map(ArticleListViewResponse::new)
                .toList();
        model.addAttribute("articles",articles);//블로그 글 리스트 저장

        return "articleList";//articleList.html 라는 뷰 조회. resource/templates/articleList.html을 찾도록 뷰의 이름을 적어준 것.
    }
    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model){
        Article article = blogService.findById(id);
        model.addAttribute("article",new ArticleViewResponse(article));//화면에서 사용할 모델에 데이터를 저장한다.

        return "article";//보여줄 화면의 템플릿 이름을 반환한다.
    }
}
