package org.myBlog.springbootdeveloper.repository;

import org.myBlog.springbootdeveloper.domain.Article;
import org.myBlog.springbootdeveloper.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> getCommentByArticleOrderById(Article article);
    Optional<Comment> findByArticleIdAndId(Long articleId, Long id);
}
