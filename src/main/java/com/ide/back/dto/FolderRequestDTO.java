package com.ide.back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FolderRequestDTO {
    private String folderName;
    private Long parentId; //null 가능
    private Long projectId;
    private Long userId;
}
