package com.ide.back.dto;

import com.ide.back.domain.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Member {

    private Long Id;
    private String nickname;
    private String email;
    private String password;
    private Timestamp createdAt;
    private Timestamp deletedAt;

    public static Member from(MemberEntity entity) {
        return new Member(
                entity.getId(),
                entity.getNickname(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getCreatedAt(),
                entity.getDeletedAt()
        );
    }
}
