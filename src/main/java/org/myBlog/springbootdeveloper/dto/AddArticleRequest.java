package org.myBlog.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myBlog.springbootdeveloper.domain.Article;

@NoArgsConstructor//기본 생성자 추가
@AllArgsConstructor//모든 필드 값을 파라미터로 받음
@Getter//모든 필드에 접근자 메서드
public class AddArticleRequest {//컨트롤러에서 요청한 본문을 받을 객체
    private String title;
    private String content;

    private String comment;

    public Article toEntity(String author){//생성자를 사용해 객체 생성
        //빌더 패턴을 사용해 DTO 를 엔티티로 만들어준다.
        return Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .comment(comment)
                .build();
    }

}
