package com.ide.back.domain;

import com.ide.back.shared.constant.ModelConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Account {

    @Id
    Long id;

    @Column(length = ModelConstants.DB_COLUMN_SIZE_FOR_EMAIL, nullable = false)
    String email;

    @Column(length = ModelConstants.DB_COLUMN_SIZE_FOR_PASSWORD, nullable = false)
    String password;

    @Column(length = ModelConstants.DB_COLUMN_SIZE_FOR_NAME, nullable = false)
    String nickname;

    @Column(nullable = false)
    LocalDateTime createdAt;

    LocalDateTime deletedAt;
}
