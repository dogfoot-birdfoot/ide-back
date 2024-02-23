package com.ide.back.controller;


import com.ide.back.dto.ProjectRequestDTO;
import com.ide.back.dto.ProjectResponseDTO;
import com.ide.back.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectRequestDTO projectRequestDTO) {
        ProjectResponseDTO createdProject = projectService.createProject(projectRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        List<ProjectResponseDTO> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id) {
        ProjectResponseDTO project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long id,@RequestParam Long userId, @RequestBody ProjectRequestDTO projectRequestDTO) {
        ProjectResponseDTO updatedProject = projectService.updateProject(id, userId, projectRequestDTO);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id,@RequestParam Long userId) {
        projectService.deleteProject(id, userId);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/member/{userId}")
//    public ResponseEntity<List<ProjectResponseDTO>> getProjectsForMember(@PathVariable Long userId) {
//        List<ProjectResponseDTO> projects = projectService.getProjectsForMember(userId);
//        return ResponseEntity.ok(projects);
//    }

    private Long getUserIdFromAuthenticationContext() {
        //시큐리티를 사용하려고 했지만 어제 실행이 안돼서 포기
        return null;
    }


}
