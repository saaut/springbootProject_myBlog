//package org.myBlog.springbootdeveloper.controller.domain;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.myBlog.springbootdeveloper.domain.Article;
//import org.myBlog.springbootdeveloper.domain.Comment;
//import org.myBlog.springbootdeveloper.domain.User;
//import org.myBlog.springbootdeveloper.repository.BlogRepository;
//import org.myBlog.springbootdeveloper.repository.CommentRepository;
//import org.myBlog.springbootdeveloper.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//import java.util.stream.IntStream;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//public class CommentRepositoryTest {
//
//    @Autowired
//    CommentRepository commentRepository;
//
//    @Autowired
//    BlogRepository articleRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @AfterEach
//    public void clear() {
//        commentRepository.deleteAll();
//        articleRepository.deleteAll();
//        userRepository.deleteAll();
//    }
//
//    @Test
//    public void 게시글_댓글_생성_조회() {
//        String content = "댓글 입니다.";
//
//        Article posts = Article.builder().build();
//        User user = User.builder().build();
//
//        commentRepository.save(Comment.builder()
//                .comment(content)
//                .user(user)
//                .article(posts)
//                .build());
//
//        List<Comment> comments = commentRepository.getCommentByArticleOrderById(posts);
//
//        Comment comment = comments.get(0);
//
//        assertThat(comment.getComment()).isEqualTo(content);
//    }
//
//    @Test
//    public void 랜덤_댓글_생성() {
//        IntStream.rangeClosed(1, 20).forEach(i -> {
//            User user = userRepository.save(User.builder().email("test" + i + "@example.com").build()); // Save a user with an email
//            Article posts = articleRepository.save(Article.builder().build());
//            Comment comment = Comment.builder()
//                    .comment(i + "번째 댓글입니다.")
//                    .user(user)
//                    .article(posts)
//                    .build();
//
//            commentRepository.save(comment);
//        });
//    }
//}