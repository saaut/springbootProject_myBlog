package org.myBlog.springbootdeveloper.controller.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.myBlog.springbootdeveloper.config.jwt.JwtProperties;
import org.myBlog.springbootdeveloper.controller.config.jwt.JwtFactory;
import org.myBlog.springbootdeveloper.domain.RefreshToken;
import org.myBlog.springbootdeveloper.domain.User;
import org.myBlog.springbootdeveloper.dto.CreateAccessTokenRequest;
import org.myBlog.springbootdeveloper.repository.RefreshTokenRepository;
import org.myBlog.springbootdeveloper.repository.UserRepository;


@SpringBootTest
@AutoConfigureMockMvc
public class TokenApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        userRepository.deleteAll();
    }
    //given
    //테스트 유저를 생성하고, jjwt 라이브러리를 이용해서 리프레시 토큰을 만들어 데이터베이스에 저장한다.
    //토큰 생성 API의 요청 본문에 리프레시 토큰을 포함하여 요청 객체를 생성한다.
    //when
    //토큰 추가 API에 요청을 보낸다. 이때 요청 타입은 JSON이며, given 절에서 미리 만들어둔 객체를 요청 본문으로 함께 보낸다.
    //then
    //응답 코드가 201Created인지 확인하고, 응답으로 온 액세스 토큰이 비어 있지 않은지 확인한다.

    @DisplayName("createNewAccessToken: 새로운 액세스 토큰을 발급한다.")
    @Test
    public void createNewAccessToken() throws Exception {
        // given
        final String url = "/api/token";

        User testUser = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());

        String refreshToekn = JwtFactory.builder()
                .claims(Map.of("id", testUser.getId()))
                .build()
                .createToken(jwtProperties);

        refreshTokenRepository.save(new RefreshToken(testUser.getId(), refreshToekn));

        CreateAccessTokenRequest request = new CreateAccessTokenRequest();
        request.setRefreshToken(refreshToekn);
        final String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());
    }
}
