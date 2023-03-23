package com.project.board.author.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler/*인터페이스를 상속받음*/{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        세션이란 로그인이 성공했을때 스프링 서버가 사용자에게 임시 할당한 저장공간
        HttpSession session = request.getSession();
        session.setAttribute("greeting", authentication.getName()+"님 반갑습니다.");
        response.sendRedirect("/");

    }
}
