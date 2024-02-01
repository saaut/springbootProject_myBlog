package org.myBlog.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.myBlog.springbootdeveloper.dto.AddUserRequest;
import org.myBlog.springbootdeveloper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserApiController{
    private final UserService userService;
    @PostMapping("/user")
    public String signup(AddUserRequest request){
        userService.save(request);//회원가입 메서드
        return "redirect:/login";//회원 가입이 완료된 이후에 로그인 페이지로 이동
    }
}
