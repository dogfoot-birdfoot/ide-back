package com.ide.back.dto;

import com.ide.back.domain.Member;
import com.ide.back.domain.Project;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
    public class ProjectRequestDTO {
        private Long userId;
        private String projectName;
        private String description;
        private List<Long> memberIds;
        private String owner;

        public Project toEntity(Member user) {
            return Project.builder()
                    .user(user)
                    .projectName(this.projectName)
                    .description(this.description)
                    .owner(this.owner)
                    .build();
        }
    }
