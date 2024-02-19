package com.ide.back.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class ProjectResponseDto {
    private Long id;
    private String projectName;
    private String description;
    private LocalDateTime createdAt;
    private String owner;
    private String author;
}
