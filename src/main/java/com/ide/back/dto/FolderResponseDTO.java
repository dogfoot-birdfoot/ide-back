package com.ide.back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FolderResponseDTO {
    private Long id;
    private String folderName;
    private LocalDateTime createAt;
    private Long parentId;
    private Long projectId;
    private Long userId;
}
