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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    // 생성자를 통한 의존성 주입
    @Autowired
    public ProjectMemberService(ProjectMemberRepository projectMemberRepository, MemberRepository memberRepository, ProjectRepository projectRepository) {
        this.projectMemberRepository = projectMemberRepository;
        this.memberRepository = memberRepository;
        this.projectRepository = projectRepository;
    }

    // 프로젝트에 멤버 추가
    public ProjectMember addMemberToProject(ProjectMemberRequestDTO requestDTO) {
        // 멤버 조회
        Member member = memberRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // 프로젝트 조회
        Project project = projectRepository.findById(requestDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // 프로젝트 멤버 객체 생성 및 저장
        ProjectMember projectMember = new ProjectMember();
        projectMember.setUser(member);
        projectMember.setProject(project);

        return projectMemberRepository.save(projectMember);
    }

    // 멤버가 프로젝트의 멤버인지 확인
    public boolean isMemberOfProject(Long memberId, Long projectId) {
        // 멤버 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        // 프로젝트 조회
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // 프로젝트 멤버 존재 여부 확인
        return projectMemberRepository.existsByUserAndProject(member, project);
    }

    // 프로젝트에서 멤버 제거
    @Transactional
    public void removeMemberFromProject(ProjectMemberRequestDTO dto) {
        // 프로젝트 조회
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // 멤버 조회
        Member member = memberRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // 프로젝트 멤버 조회
        ProjectMember projectMember = projectMemberRepository.findByProjectAndUser(project, member);
        if (projectMember == null) {
            throw new RuntimeException("Member is not part of this project");
        }

        // 프로젝트 멤버 제거
        projectMemberRepository.delete(projectMember);
    }

    // 멤버에 속한 프로젝트 ID 목록 조회
    public List<Long> getProjectIdsForMember(Long userId) {
        return projectMemberRepository.findProjectIdsByUserId(userId);
    }

    // 프로젝트 멤버 업데이트
    @Transactional
    public void updateProjectMembers(Project project, List<Long> memberIds) {
        // 현재 프로젝트 멤버 조회
        List<ProjectMember> currentMembers = projectMemberRepository.findByProject(project);

        // 업데이트 목록에 없는 기존 멤버 제거
        List<ProjectMember> membersToRemove = currentMembers.stream()
                .filter(member -> !memberIds.contains(member.getUser().getId()))
                .collect(Collectors.toList());

        projectMemberRepository.deleteAll(membersToRemove);

        // 새 멤버 추가 (기존에 없는 멤버만)
        List<Long> existingMemberIds = currentMembers.stream()
                .map(member -> member.getUser().getId())
                .collect(Collectors.toList());

        List<Long> newMemberIds = memberIds.stream()
                .filter(memberId -> !existingMemberIds.contains(memberId))
                .collect(Collectors.toList());

        List<ProjectMember> newMembers = newMemberIds.stream()
                .map(memberId -> {
                    // 새 멤버 조회 및 프로젝트 멤버 객체 생성
                    Member member = memberRepository.findById(memberId)
                            .orElseThrow(() -> new RuntimeException("Member not found with ID: " + memberId));

                    ProjectMember projectMember = new ProjectMember();
                    projectMember.setUser(member);
                    projectMember.setProject(project);
                    return projectMember;
                })
                .collect(Collectors.toList());

        // 새 프로젝트 멤버 저장
        projectMemberRepository.saveAll(newMembers);
    }
}
