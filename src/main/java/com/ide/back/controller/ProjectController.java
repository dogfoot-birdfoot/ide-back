package com.ide.back.controller;


import com.ide.back.dto.ProjectMemberResponseDTO;
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
    // 프로젝트 생성
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectRequestDTO projectRequestDTO) {
        ProjectResponseDTO createdProject = projectService.createProject(projectRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }

    // 모든 프로젝트 조회
    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects(@RequestParam Long userId) {
        List<ProjectResponseDTO> projects = projectService.getAllProjects(userId);
        return ResponseEntity.ok(projects);
    }

    //특정 프로젝트 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id ,@RequestParam Long userId) {
        ProjectResponseDTO project = projectService.getProjectById(id, userId);
        return ResponseEntity.ok(project);
    }
    //프로젝트 수정
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long id, @RequestBody ProjectRequestDTO projectRequestDTO) {
        ProjectResponseDTO updatedProject = projectService.updateProject(id, projectRequestDTO);
        return ResponseEntity.ok(updatedProject);
    }

    // 프로젝트 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
    // 프로젝트 멤버 조회
    @GetMapping("/{id}/members")
    public ResponseEntity<List<ProjectMemberResponseDTO>> getProjectMembers(@PathVariable Long id) {
        List<ProjectMemberResponseDTO> members = projectService.getProjectMembers(id);
        return ResponseEntity.ok(members);
    }
}