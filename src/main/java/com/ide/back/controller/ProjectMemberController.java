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

    // 프로젝트에 멤버 추가하는 API
    @PostMapping("/add")
    public ResponseEntity<String> addMemberToProject(@RequestBody ProjectMemberRequestDTO requestDTO) {
        // 서비스를 통해 프로젝트에 멤버 추가
        projectMemberService.addMemberToProject(requestDTO);
        // 생성 상태 응답과 함께 성공 메시지 반환
        return ResponseEntity.status(HttpStatus.CREATED).body("Member added to project successfully.");
    }

    // 프로젝트에서 멤버 제거하는 API (현재 주석 처리됨)
    // @DeleteMapping("/remove")
    // public ResponseEntity<String> removeMemberFromProject(@RequestBody ProjectMemberRequestDTO requestDTO) {
    //     projectMemberService.removeMemberFromProject(requestDTO);
    //     return ResponseEntity.status(HttpStatus.OK).body("Member removed from project successfully.");
    // }

    // 특정 멤버가 프로젝트의 멤버인지 확인하는 API (GET 방식)
    @GetMapping("/check")
    public ResponseEntity<Boolean> isMemberOfProject(@RequestParam Long memberId, @RequestParam Long projectId) {
        // 서비스를 통해 멤버십 확인
        boolean isMember = projectMemberService.isMemberOfProject(memberId, projectId);
        // 확인 결과 반환
        return new ResponseEntity<>(isMember, HttpStatus.OK);
    }

    // 특정 멤버가 프로젝트의 멤버인지 확인하는 API (POST 방식)
    @PostMapping("/check")
    public ResponseEntity<Boolean> isMemberOfProject(@RequestBody ProjectMemberRequestDTO checkDTO) {
        // 서비스를 통해 멤버십 확인
        boolean isMember = projectMemberService.isMemberOfProject(checkDTO.getUserId(), checkDTO.getProjectId());
        // 확인 결과 반환
        return new ResponseEntity<>(isMember, HttpStatus.OK);
    }
}
