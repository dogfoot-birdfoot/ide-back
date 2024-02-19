package com.ide.back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class MemberDto {

    private Long Id;
    private String nickname;
    private String email;
    private String password;
    private Timestamp createdAt;
    private Timestamp deletedAt;

    public static MemberDto from(com.ide.back.domain.Member entity) {
        return new MemberDto(
                entity.getId(),
                entity.getNickname(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getCreatedAt(),
                entity.getDeletedAt()
        );
    }
}
