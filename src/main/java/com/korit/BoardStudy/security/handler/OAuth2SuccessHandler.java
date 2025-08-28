package com.korit.BoardStudy.security.handler;

import com.korit.BoardStudy.entity.OAuth2User;
import com.korit.BoardStudy.entity.User;
import com.korit.BoardStudy.repository.OAuth2UserRepository;
import com.korit.BoardStudy.repository.UserRepository;
import com.korit.BoardStudy.security.jwt.JwtUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    // 로그인 후에 인증 객체 만들어지면 어떻게 해야할지 처리해주는 곳
    // 연동된 적 있나 없나 확인 -> 회원가입? 연동?

    @Autowired
    private OAuth2UserRepository oAuth2UserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String provider = defaultOAuth2User.getAttribute("provider");
        String providerUserId = defaultOAuth2User.getAttribute("id");
        String email = defaultOAuth2User.getAttribute("email");

        // 회원가입된 사람 있는지 확인
        Optional<OAuth2User> optionalOAuth2User = oAuth2UserRepository.getOAuth2UserByProviderAndProviderUserId(provider, providerUserId);
        if(optionalOAuth2User.isEmpty()) {
            response.sendRedirect("http://localhost:5173/auth/oauth2?provider=" + provider
                    + "&providerUserId=" + providerUserId + "&email=" + email);
            return;
        }

        OAuth2User oAuth2User = optionalOAuth2User.get();
        Optional<User> optionalUser = userRepository.getUserByUserId(oAuth2User.getUserId());

        String accessToken = null;
        if(optionalUser.isPresent()) {
            accessToken = jwtUtils.generateAccessToken(optionalUser.get().getUserId().toString());
        }

        response.sendRedirect("http://localhost:5173/auth/oauth2/signin?accessToken=" + accessToken);
    }
}
