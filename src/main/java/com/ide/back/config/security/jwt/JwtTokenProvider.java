package com.ide.back.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final UserDetailsService detailsService;

//    토큰 생성( access, refresh ) , 삭제 , 유효성 검사

    @Value("${jwt.secret}")
    private String secretKeyString;

    private SecretKey secretKey;

    @Value("${jwt.access-exp}")
    private Long accessExp;

    @Value("${jwt.refresh-exp}")
    private Long refreshExp;


    @PostConstruct
    protected void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKeyString);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(Authentication authentication){

        Date now = new Date();
        Date exp = new Date(now.getTime() + accessExp * 1000);

        UserDetails principal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .claim("userId", principal.getUsername())
                .claim("role", principal.getAuthorities())
                .expiration(exp)
                .issuedAt(new Date())
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication){

        Date now = new Date();
        Date exp = new Date(now.getTime() + refreshExp * 1000);

        // TODO : 여기에 db 저장 로직 추가해서 refresh token을 저장하자

        return Jwts.builder()
                .claim("userId", authentication.getPrincipal())
                .claim("role", authentication.getAuthorities())
                .expiration(exp)
                .issuedAt(now)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token){
        Claims claims = Jwts.parser().verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        log.info("Authentication claims : {} - {}", claims.get("userId"), claims.get("role"));

        UserDetails userDetails = detailsService.loadUserByUsername(claims.get("userId", String.class));

        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

    public String getUserId(String token){
        return Jwts.parser()
                .verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload().get("userId", String.class);
    }

    public String getRole(String token){
        return Jwts.parser()
                .verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload().get("role", String.class);
    }

    // bearer 토큰
    public String resolveToken(String token){
        if (token != null && token.startsWith("Bearer")){
            return token.substring(7);
        }
        return null;
    }

    public boolean validationToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().after(new Date());
    }

}