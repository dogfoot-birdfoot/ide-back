package com.ide.back.controller;

import com.ide.back.dto.ProjectDto;
import com.ide.back.dto.ProjectResponseDto;
import com.ide.back.exception.ProjectNotFoundException;
import com.ide.back.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDto> createProject(@RequestBody ProjectDto projectDto) {
        ProjectResponseDto createProject = projectService.createProject(projectDto);
        return ResponseEntity.ok(createProject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ProjectNotFoundException("ID " + id));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getAllProjects() {
        List<ProjectResponseDto> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> updateProject(@PathVariable Long id, @RequestBody ProjectDto projectDto) {
        ProjectResponseDto updateProject = projectService.updateProject(id, projectDto);
        return ResponseEntity.ok(updateProject);
    }

    @DeleteMapping("/id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }


}
