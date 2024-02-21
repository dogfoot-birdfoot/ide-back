package com.ide.back.service;

import com.ide.back.domain.Member;
import com.ide.back.domain.Project;
import com.ide.back.domain.ProjectMember;
import com.ide.back.dto.ProjectMemberResponseDTO;
import com.ide.back.dto.ProjectRequestDTO;
import com.ide.back.dto.ProjectResponseDTO;
import com.ide.back.repository.MemberRepository;
import com.ide.back.repository.ProjectMemberRepository;
import com.ide.back.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final ProjectMemberService projectMemberService;

    private final ProjectMemberRepository projectMemberRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, MemberRepository memberRepository, ProjectMemberService projectMemberService, ProjectMemberRepository projectMemberRepository) {
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
        this.projectMemberService = projectMemberService;
        this.projectMemberRepository = projectMemberRepository;
    }

    //프로젝트 생성
    public ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO) {
        Member member = memberRepository.findById(projectRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Project project = projectRequestDTO.toEntity(member);
        project = projectRepository.save(project);

        ProjectMember projectMember = new ProjectMember();
        projectMember.setUser(member);
        projectMember.setProject(project);
        projectMemberRepository.save(projectMember);

        return convertToResponseDTO(project);
    }

    //프로젝트 검색
    public ProjectResponseDTO getProjectById(Long id,Long userId) {
        if (!isMemberOfProject(userId, id)) {
            throw new RuntimeException("User is not a member of the requested project");
        }
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return convertToResponseDTO(project);
    }

    //유저 프로젝트 검색
    public List<ProjectResponseDTO> getAllProjects(Long userId) {
        Member user = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Project> projects = projectRepository.findByUser(user);

        return projects.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // 프로젝트 수정
    @Transactional
    public ProjectResponseDTO updateProject(Long id, ProjectRequestDTO projectRequestDTO) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        if (projectRequestDTO.getUserId() != null) {
            Member user = memberRepository.findById(projectRequestDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            if (!isMemberOfProject(user.getId(), id)) {
                throw new RuntimeException("User is not a member of the project");
            }
            project.setUser(user);
        }

        project.updateProjectFromDTO(projectRequestDTO);
        project = projectRepository.save(project);
        return convertToResponseDTO(project);
    }


    // 프로젝트 삭제
    @Transactional
    //프로젝트 삭제
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    // 프로젝트 멤버 조회
    public List<ProjectMemberResponseDTO> getProjectMembers(Long projectId) {
        List<ProjectMember> projectMembers = projectMemberRepository.findByProjectId(projectId);
        return projectMembers.stream()
                .map(pm -> {
                    ProjectMemberResponseDTO dto = new ProjectMemberResponseDTO();
                    dto.setId(pm.getId());
                    dto.setUserId(pm.getUser().getId());
                    dto.setProjectId(pm.getProject().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    // 프로젝트 멤버 확인
    public boolean isMemberOfProject(Long memberId, Long projectId) {
        return projectMemberService.isMemberOfProject(memberId, projectId);
    }



    // DTO로 변환하는 메서드
    private ProjectResponseDTO convertToResponseDTO(Project project) {
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setId(project.getId());
        dto.setProjectName(project.getProjectName());
        dto.setDescription(project.getDescription());
        dto.setCreatedAt(project.getCreatedAt());
        dto.setOwner(project.getOwner());
        dto.setAuthor(project.getAuthor());
        return dto;

    }
}
