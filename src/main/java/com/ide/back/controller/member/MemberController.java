package com.ide.back.controller;

import com.ide.back.api.response.ApiResponse;
import com.ide.back.dto.MemberDto;
import com.ide.back.controller.request.MemberJoinRequest;
import com.ide.back.controller.response.MemberJoinResponse;
import com.ide.back.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signUp")
    public ApiResponse<MemberJoinResponse> signUp(@RequestBody MemberJoinRequest request) {
        MemberDto member = memberService.join(request.getEmail(), request.getPassword(), request.getNickname());
        MemberJoinResponse memberJoinResponse = MemberJoinResponse.fromMember(member, null, null);
        return new ApiResponse<>(memberJoinResponse);
    }

    @PostMapping("/login")
    public ApiResponse<MemberJoinResponse> login(@RequestBody MemberJoinRequest request) {
        return memberService.login(request.getEmail(), request.getPassword());
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        memberService.logout(token);
        return new ApiResponse<>("로그아웃 되었습니다");
    }

}
