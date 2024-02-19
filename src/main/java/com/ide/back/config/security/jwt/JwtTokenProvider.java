package com.ide.back.config.security.jwt;

import com.ide.back.config.security.CustomUserDetailsService;
import com.ide.back.domain.RefreshToken;
import com.ide.back.repository.RefreshTokenRepository;
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
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final CustomUserDetailsService detailsService;
    private final RefreshTokenRepository refreshTokenRepository;

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
        //this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateAccessToken(Authentication authentication) {

        Date now = new Date();
        Date exp = new Date(now.getTime() + accessExp * 1000);

        UserDetails principal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .claim("email", principal.getUsername())
                //.claim("role", principal.getAuthorities())
                .expiration(exp)
                .issuedAt(new Date())
                .signWith(secretKey)//.signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Date now = new Date();
        Date exp = new Date(now.getTime() + refreshExp * 1000);

        // TODO : 여기에 db 저장 로직 추가해서 refresh token을 저장하자

        String refreshToken = Jwts.builder()
                .claim("email", email)
                //.claim("role", authentication.getAuthorities())
                .expiration(exp)
                .issuedAt(now)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();

        RefreshToken tokenEntity = new RefreshToken();
        tokenEntity.setEmail(email);
        tokenEntity.setToken(refreshToken);
        refreshTokenRepository.save(tokenEntity);

        return refreshToken;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        log.info("Authentication claims : {}", claims.get("email"));

        UserDetails userDetails = detailsService.loadUserByUsername(claims.get("email", String.class));
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

    public String getUserId(String token) {
        return Jwts.parser()
                .verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload().get("userId", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload().get("role", String.class);
    }

    // bearer 토큰
    public String resolveToken(String token) {
        if (token != null && token.startsWith("Bearer")) {
            return token.substring(7);
        }
        return null;
    }

    public boolean validationToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().after(new Date());
    }

}