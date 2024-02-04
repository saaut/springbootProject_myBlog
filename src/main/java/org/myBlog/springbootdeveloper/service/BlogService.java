package org.myBlog.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.myBlog.springbootdeveloper.domain.Article;
import org.myBlog.springbootdeveloper.dto.AddArticleRequest;
import org.myBlog.springbootdeveloper.dto.UpdateArticleRequest;
import org.myBlog.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    //블로그 글 추가 메서드
    public Article save(AddArticleRequest request,String userName){//AddArticleRequest 클래스에 저장된 값들을 article 데이터베이스에 저장한다.
        return blogRepository.save(request.toEntity(userName));
    }
    public List<Article> findAll(){//블로그 모든 글 조회
        return blogRepository.findAll();
    }
    public Article findById(long id){
        return blogRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found:"+id));
    }
    public void delete(long id){
        blogRepository.deleteById(id);
    }
    @Transactional
    public Article update(long id, UpdateArticleRequest request){
        Article article=blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found:"+id));

        article.update(request.getTitle(),request.getContent());
        return article;
    }
}
