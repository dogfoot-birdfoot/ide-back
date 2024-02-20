package com.ide.back.dto;

import com.ide.back.domain.Member;
import com.ide.back.domain.Project;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProjectRequestDTO {
    private Long userId;
    private String projectName;
    private String description;
    private String owner;
    private String author;

    public Project toEntity(Member user) {
        return Project.builder()
                .projectName(this.projectName)
                .description(this.description)
                .owner(this.owner)
                .author(this.author)
                .build();
    }
}
