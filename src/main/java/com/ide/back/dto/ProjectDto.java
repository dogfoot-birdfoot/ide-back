package com.ide.back.dto;

import com.ide.back.domain.Project;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class projectDto {
    private String projectName;
    private String description;
    private String owner;
    private String author;

    public Project toEntity() {
        return Project.builder()
                .projectName(projectName)
                .description(description)
                .owner(owner)
                .author(author)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
