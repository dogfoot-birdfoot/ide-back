package com.ide.back.config.security.filters;

import com.ide.back.config.security.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider tokenProvider;

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter("userId");
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter("userPassword");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {


        String userId = obtainUsername(request);
        String password = obtainPassword(request);

        log.info("LoginFilter.attemptAuthentication -   {}", userId);

        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(userId, password, null));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String token = tokenProvider.generateAccessToken(authResult);

        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
    }
}
