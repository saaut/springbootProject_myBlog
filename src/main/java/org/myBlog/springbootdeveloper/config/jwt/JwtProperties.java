package org.myBlog.springbootdeveloper.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {//이슈 발급자와 비밀 키를 변수로 접근하는 데 사용
    private String issuer;
    private String secretKey;
}
