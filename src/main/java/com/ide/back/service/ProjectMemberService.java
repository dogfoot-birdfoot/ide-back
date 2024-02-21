package com.ide.back.service;

import com.ide.back.domain.Member;
import com.ide.back.domain.Project;
import com.ide.back.domain.ProjectMember;
import com.ide.back.dto.ProjectMemberRequestDTO;
import com.ide.back.repository.MemberRepository;
import com.ide.back.repository.ProjectMemberRepository;
import com.ide.back.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectMemberService(ProjectMemberRepository projectMemberRepository, MemberRepository memberRepository, ProjectRepository projectRepository) {
        this.projectMemberRepository = projectMemberRepository;
        this.memberRepository = memberRepository;
        this.projectRepository = projectRepository;
    }

    // 프로젝트 멤버 추가 메서드
    public ProjectMember addMemberToProject(ProjectMemberRequestDTO requestDTO) {
        // 유저하고 프로젝트 검색
        Member member = memberRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Project project = projectRepository.findById(requestDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // 새로운 프로젝트 엔터티 생성, 저장
        ProjectMember projectMember = new ProjectMember();
        projectMember.setUser(member);
        projectMember.setProject(project);

        return projectMemberRepository.save(projectMember);
    }

    // 프로젝트 멤버 확인 메서드
    public boolean isMemberOfProject(Long memberId, Long projectId) {
        // 유저와 프로젝트 검색 후 멤버인지 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        return projectMemberRepository.existsByUserAndProject(member, project);
    }
}
