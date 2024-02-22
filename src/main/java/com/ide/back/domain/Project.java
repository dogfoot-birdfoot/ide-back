package com.ide.back.domain;

import com.ide.back.dto.ProjectRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 어떻게 구현해야 하는지 모름
    private LocalDateTime deletedAt;

    @Column(nullable = false, length = 30)
    private String owner;

    @Column(nullable = false, length = 30)
    private String author;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Folder> folders = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectMember> projectMembers = new ArrayList<>();

//    public static class ProjectBuilder {
//        public ProjectBuilder() {
//            this.folders = new ArrayList<>();
//            this.files = new ArrayList<>();
//        }
//    }

    @Builder
    public Project(Member user, String projectName, String description, LocalDateTime createdAt, String owner, String author) {
        this.user = user;
        this.projectName = projectName;
        this.description = description;
        this.createdAt = createdAt;
        this.owner = owner;
        this.author = author;
    }

    public void updateProjectFromDTO(ProjectRequestDTO updatedProjectDTO) {
        this.projectName = updatedProjectDTO.getProjectName();
        this.description = updatedProjectDTO.getDescription();
        this.owner = updatedProjectDTO.getOwner();
        this.author = updatedProjectDTO.getAuthor();
    }
}

