package org.myBlog.springbootdeveloper.config.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.myBlog.springbootdeveloper.config.jwt.TokenProvider;
import org.myBlog.springbootdeveloper.domain.RefreshToken;
import org.myBlog.springbootdeveloper.domain.User;
import org.myBlog.springbootdeveloper.repository.RefreshTokenRepository;
import org.myBlog.springbootdeveloper.service.UserService;
import org.myBlog.springbootdeveloper.util.CookieUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Component//인증 성공 시 실행할 핸들러
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public static final String REDIRECT_PATH = "/articles";

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final UserService userService;

    @Override//OAuth2 인증 성공 시 실행되는 메서드
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)throws IOException{
        //사용자 찾기
        OAuth2User oAuth2User=(OAuth2User) authentication.getPrincipal();
        User user =userService.findByEmail((String) oAuth2User.getAttributes().get("email"));

        //1.토큰 제공자를 사용해 리프레시 토큰 생성->유저 아이디와 함께 데이터 베이스에 저장->만료 시 재발급 요청을 위해 쿠키에 저장
        String refreshToken=tokenProvider.generateToken(user,REFRESH_TOKEN_DURATION);
        saveRefreshToken(user.getId(),refreshToken);
        addRefreshTokenToCookie(request,response,refreshToken);
        //2.액세스 토큰 생성->패스에 액세스 토큰 추가. 리다이렉션할 URL 완성
        String accessToken=tokenProvider.generateToken(user,ACCESS_TOKEN_DURATION);
        String targetUrl=getTargetUrl(accessToken);
        //3.보안을 위해 인증 관련 설정값, 요청 정보를 저장하는 쿠키 제거
        clearAuthenticationAttributes(request,response);
        //4.액세스 토큰이 포함된 URL로 리다이렉트
        getRedirectStrategy().sendRedirect(request,response,targetUrl);
    }
    //생성된 리프레시 토큰을 전달받아 데이터베이스에 저장
    private void saveRefreshToken(Long userId, String newRefreshToken) {
        RefreshToken refreshToken= refreshTokenRepository.findByUserId(userId)
                .map(entity->entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId,newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }
    //생성된 리프레시 토큰을 쿠키에 저장
    private void addRefreshTokenToCookie(HttpServletRequest request,HttpServletResponse response, String refreshToken) {
        int cookieMaxAge=(int) REFRESH_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(request,response,REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response,REFRESH_TOKEN_COOKIE_NAME,refreshToken,cookieMaxAge);
    }
    //인증 관련 설정값, 쿠키 제거
    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request,response);
    }
    //액세스 토큰을 패스에 추가
    private String getTargetUrl(String token) {
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
                .queryParam("token",token)
                .build()
                .toUriString();
    }
}
