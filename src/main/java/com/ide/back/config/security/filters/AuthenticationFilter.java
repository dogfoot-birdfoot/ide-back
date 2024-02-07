package com.ide.back.config.security.filters;


import com.ide.back.config.security.jwt.JwtTokenProvider;
import com.ide.back.shared.constant.WhitePath;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(Arrays.asList(WhitePath.WHITE_LIST).contains(request.getServletPath())){
            filterChain.doFilter(request, response);
            return;
        }

        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        String resolve = tokenProvider.resolveToken(bearerToken);

        if(resolve != null && tokenProvider.validationToken(resolve)){
            try{
                Authentication authentication = tokenProvider.getAuthentication(resolve);

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);

            }catch (Exception e){
                log.error("Authentication Filter Exception : {}", e.getMessage());
            }
        }else{
            log.warn("비정상적 토큰으로 요청되었습니다.");
        }

        filterChain.doFilter(request, response);
    }
}

