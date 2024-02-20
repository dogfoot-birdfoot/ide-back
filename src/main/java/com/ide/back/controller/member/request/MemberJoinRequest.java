package com.ide.back.controller.member.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberJoinRequest {
    private String email;
    private String password;
    private String nickname;
}
