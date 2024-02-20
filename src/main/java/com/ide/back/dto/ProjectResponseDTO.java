package com.ide.back.dto;

import com.ide.back.domain.Project;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProjectResponseDTO {
    private Long id;
    private String projectName;
    private String description;
    private LocalDateTime createdAt;
    private String owner;
    private String author;
}


