package org.myBlog.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.myBlog.springbootdeveloper.dto.CreateAccessTokenRequest;
import org.myBlog.springbootdeveloper.dto.CreateAccessTokenResponse;
import org.myBlog.springbootdeveloper.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenApiController {
    private final TokenService tokenService;
    @PostMapping("/api/token")//요청이 오면
    //토큰 서비스에서 리프레시 토큰을 기반으로 새로운 액세스 토큰을 만들어준다.
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request){
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
}

