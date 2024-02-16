package com.ide.back.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Folder {

    @Id
    Long id;

    String name;

    LocalDateTime createdAt;

    LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    Folder parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    Account user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    Project project;
}
