package com.ide.back.dto;

import com.ide.back.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MemberDto {

    private Long Id;
    private String nickname;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public static MemberDto from(Member entity) {
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
