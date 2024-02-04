package org.myBlog.springbootdeveloper.config.oauth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.myBlog.springbootdeveloper.util.CookieUtil;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.util.WebUtils;

//OAuth2에 필요한 정보를 세션이 아닌 쿠키에 저장해서 쓰도록 하는 인증 요청 상태 저장소
public class OAuth2AuthorizationRequestBaseOnCookieRepository implements
        AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    public final static String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    private final static int COOKIE_EXPIRE_SECONDS = 18000;

    @Override//OAuth2 권한 요청을 쿠키에서 제거한다.
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response){
        return this.loadAuthorizationRequest(request);
    }
    @Override//OAuth2 권한 요청을 쿠키에서 로드한다.
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Cookie cookie= WebUtils.getCookie(request,OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        return CookieUtil.deserialize(cookie,OAuth2AuthorizationRequest.class);//쿠키 값을 역직렬화
    }

    @Override//OAuth2 권한 요청을 쿠키에 저장한다.
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest,
                                         HttpServletRequest request, HttpServletResponse response) {
        if(authorizationRequest==null){
            removeAuthorizationRequestCookies(request,response);//요청이 없으면 쿠키 삭제
            return;
        }
        CookieUtil.addCookie(response,OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,//새 쿠키 추가
                CookieUtil.serialize(authorizationRequest),COOKIE_EXPIRE_SECONDS);
    }
//OAuth2 권한 요청 쿠키를 삭제한다.
    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request,response,OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
    }

}
