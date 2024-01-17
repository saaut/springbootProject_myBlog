package org.myBlog.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.myBlog.springbootdeveloper.domain.Article;
import org.myBlog.springbootdeveloper.dto.AddArticleRequest;
import org.myBlog.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    //블로그 글 추가 메서드
    public Article save(AddArticleRequest request){//AddArticleRequest 클래스에 저장된 값들을 article 데이터베이스에 저장한다.
        return blogRepository.save(request.toEntity());
    }
    public List<Article> findAll(){//블로그 모든 글 조회
        return blogRepository.findAll();
    }
}
