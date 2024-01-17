package org.myBlog.springbootdeveloper.repository;

import org.myBlog.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article,Long> {//JpaRepository에서 제공하는 여러 메서드 사용 가능

}
