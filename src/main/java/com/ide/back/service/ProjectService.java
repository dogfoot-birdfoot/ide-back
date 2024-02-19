package com.ide.back.service;

import com.ide.back.domain.Project;
import com.ide.back.dto.ProjectDto;
import com.ide.back.dto.ProjectResponseDto;
import com.ide.back.exception.ProjectNotFoundException;
import com.ide.back.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    //프로젝트 생성
    public ProjectResponseDto createProject(ProjectDto dto) {
        Project project = Project.builder()
                .projectName(dto.getProjectName())
                .description(dto.getDescription())
                .owner(dto.getOwner())
                .author(dto.getAuthor())
                .build();
        project = projectRepository.save(project);
        return convertToResponseDto(project);
    }

    //프로젝트 검색
    public Optional<ProjectResponseDto> getProjectById(Long id) {
        return projectRepository.findById(id)
                .map(this::convertToResponseDto);
    }

    //모든 프로젝트 검색
    public List<ProjectResponseDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    //프로젝트 수정
    public ProjectResponseDto updateProject(Long id, ProjectDto dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("ID: " + id));
        project.setProjectName(dto.getProjectName());
        project.setDescription(dto.getDescription());
        project.setOwner(dto.getOwner());
        project.setAuthor(dto.getAuthor());
        project = projectRepository.save(project);
        return convertToResponseDto(project);
    }

    @Transactional
    //프로젝트 삭제
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("ID " + id));
        projectRepository.delete(project);
    }

    private ProjectResponseDto convertToResponseDto(Project project) {
        return ProjectResponseDto.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .owner(project.getOwner())
                .author(project.getAuthor())
                .build();
    }
}
