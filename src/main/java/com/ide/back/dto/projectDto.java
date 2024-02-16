package com.ide.back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class projectDto {
    private String projectName;
    private String description;
    private String owner;
    private String author;
}
