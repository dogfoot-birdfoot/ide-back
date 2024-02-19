    package com.ide.back.service;


    import com.ide.back.api.response.ApiError;
    import com.ide.back.api.response.ApiResponse;
    import com.ide.back.config.security.jwt.JwtTokenProvider;
    import com.ide.back.controller.response.MemberJoinResponse;
    import com.ide.back.domain.Member;
    import com.ide.back.dto.MemberDto;
    import com.ide.back.exception.IdeApplicationException;
    import com.ide.back.repository.MemberRepository;
    import com.ide.back.shared.type.ApiErrorType;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    @Service
    @RequiredArgsConstructor
    public class MemberService {

        private final MemberRepository memberRepository;
        private final AuthenticationManager authenticationManager;
        private final JwtTokenProvider jwtTokenProvider;
        private final BCryptPasswordEncoder passwordEncoder;


        @Transactional
        public MemberDto join(String email, String password, String nickname) {
            // 이메일 중복 체크
            memberRepository.findByEmail(email).ifPresent(it -> {
                ApiError apiError = new ApiError(ApiErrorType.CONFLICT, "409", "DUPLICATED EMAIL");
                throw new IdeApplicationException(apiError, String.format("입력하신 %s 이 이미 존재합니다.", email));
            });

            // 회원명 중복 체크
            memberRepository.findByNickname(nickname).ifPresent(it -> {
                ApiError apiError = new ApiError(ApiErrorType.CONFLICT, "409", "DUPLICATED NAME");
                throw new IdeApplicationException(apiError, String.format("입력하신 %s 이 이미 존재합니다.", nickname));
            });

            // 회원가입
            Member member = memberRepository.save(Member.of(email, passwordEncoder.encode(password), nickname));

            return MemberDto.from(member);
        }


        public ApiResponse<MemberJoinResponse> login(String email, String password) {
            // 회원가입 여부 체크
            Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                    new IdeApplicationException(new ApiError(ApiErrorType.NOT_FOUND, "404", "존재하지 않는 이메일입니다.")));

            // 비밀번호 체크
            if (!passwordEncoder.matches(password, member.getPassword())) {
                throw new IdeApplicationException(new ApiError(ApiErrorType.BAD_REQUEST, "400", "비밀번호가 일치하지 않습니다."));
            }

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = jwtTokenProvider.generateAccessToken(authentication);
            String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

            MemberJoinResponse response = new MemberJoinResponse(member.getId(), member.getEmail(), accessToken, refreshToken);

            return new ApiResponse<>(response);
        }
    }
