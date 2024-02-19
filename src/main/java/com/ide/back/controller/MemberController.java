package com.ide.back.controller;

import com.ide.back.api.response.ApiResponse;
import com.ide.back.controller.request.MemberJoinRequest;
import com.ide.back.controller.response.MemberJoinResponse;
import com.ide.back.domain.MemberEntity;
import com.ide.back.dto.Member;
import com.ide.back.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signUp")
    public ApiResponse<MemberJoinResponse> signUp(@RequestBody MemberJoinRequest request) {
        Member member = memberService.join(request.getEmail(), request.getPassword(), request.getNickname());
        MemberJoinResponse memberJoinResponse = MemberJoinResponse.fromMember(member, null, null);
        return new ApiResponse<>(memberJoinResponse);
    }

    @PostMapping("/login")
    public ApiResponse<MemberJoinResponse> login(@RequestBody MemberJoinRequest request) {
        return memberService.login(request.getEmail(), request.getPassword());
    }


}
