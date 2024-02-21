package com.ide.back.controller;

import com.ide.back.dto.ProjectMemberRequestDTO;
import com.ide.back.service.ProjectMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project-members")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @Autowired
    public ProjectMemberController(ProjectMemberService projectMemberService) {
        this.projectMemberService = projectMemberService;
    }
    // 프로젝트 멤버 추가
    @PostMapping("/add")
    public ResponseEntity<Void> addMemberToProject(@RequestBody ProjectMemberRequestDTO requestDTO) {
        projectMemberService.addMemberToProject(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 프로젝트 멤버 확인(URL 쿼리 문자열 사용)
    @GetMapping("/check")
    public ResponseEntity<Boolean> isMemberOfProject(@RequestParam Long memberId, @RequestParam Long projectId) {
        boolean isMember = projectMemberService.isMemberOfProject(memberId, projectId);
        return new ResponseEntity<>(isMember, HttpStatus.OK);
    }

    // 프로젝트 멤버 확인 (요청DTO 사용)
    @PostMapping("/check")
    public ResponseEntity<Boolean> isMemberOfProject(@RequestBody ProjectMemberRequestDTO checkDTO) {
        boolean isMember = projectMemberService.isMemberOfProject(checkDTO.getUserId(), checkDTO.getProjectId());
        return new ResponseEntity<>(isMember, HttpStatus.OK);
    }

}
