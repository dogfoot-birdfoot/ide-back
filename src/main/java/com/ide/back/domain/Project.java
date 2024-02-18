package com.ide.back.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member user;

    @Column(name = "project_name", nullable = false, length = 50)
    private String projectName;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    @Column(nullable = false, length = 30)
    private String owner;

    @Column(nullable = false, length = 30)
    private String author;

    @OneToMany(mappedBy = "project")
    private List<Folder> folders;

    @OneToMany(mappedBy = "project")
    private List<File> files;
}

