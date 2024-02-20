package com.ide.back.dto;

import com.ide.back.domain.Project;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ProjectResponseDTO {
    private Long id;
    private Long userId;
    private String projectName;
    private String description;
    private LocalDateTime createdAt;
    private String owner;
    private String author;

    public static ProjectResponseDTO fromEntity(Project project) {
        if (project == null) {
            return null; // Return null if the project is null
        }

        Long userId = null;
        if (project.getUser() != null) {
            userId = project.getUser().getId();
        }

        return ProjectResponseDTO.builder()
                .id(project.getId())
                .userId(project.getUser().getId()) // Assuming getUser() returns a non-null Member object with getId() method
                .projectName(project.getProjectName())
                .description(project.getDescription())
                .owner(project.getOwner())
                .author(project.getAuthor())
                .createdAt(project.getCreatedAt())
                .build();
    }

    public static List<ProjectResponseDTO> fromEntities(List<Project> projects) {
        return projects.stream().map(ProjectResponseDTO::fromEntity).collect(Collectors.toList());
    }
}


