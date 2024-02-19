package com.ide.back.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "member")
@SQLDelete(sql = "UPDATE member SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at is NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 50)
    private String nickname;

    @Email
    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;


    @OneToMany(mappedBy = "user")
    private List<File> files;

    @OneToMany(mappedBy = "user")
    private List<Folder> folders;

    @OneToMany(mappedBy = "user")
    private List<Project> projects;

    @OneToMany(mappedBy = "user")
    private List<ProjectMember> projectMembers;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Timestamp.from(Instant.now()).toLocalDateTime();
    }

    public static Member of(String email, String password, String nickname) {
        Member entity = new Member();
        entity.setEmail(email);
        entity.setPassword(password);
        entity.setNickname(nickname);
        return entity;
    }

}
