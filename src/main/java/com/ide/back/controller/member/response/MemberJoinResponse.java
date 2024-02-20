package com.ide.back.controller.member.response;

import com.ide.back.dto.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberJoinResponse {

    private Long id;
    private String email;
    private String accessToken;
    private String refreshToken;


    public static MemberJoinResponse fromMember(Member member, String accessToken, String refreshToken) {
        return new MemberJoinResponse(
                member.getId(),
                member.getEmail(),
                accessToken,
                refreshToken
        );
    }

}
