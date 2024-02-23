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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

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

    // 프로젝트 생성 메서드
    @org.springframework.transaction.annotation.Transactional
    public ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO) {
        // 유저 ID로 유저를 조회하여 가져오기
        Member user = memberRepository.findById(projectRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));


        // 프로젝트 엔티티 생성
        Project project = projectRequestDTO.toEntity(user);

        // 프로젝트 저장
        project = projectRepository.save(project);

        // 프로젝트 멤버 추가
        addProjectMembers(project, projectRequestDTO.getMemberIds());

        // 프로젝트를 응답 DTO로 변환하여 반환
        return convertToResponseDTO(project);
    }

    public List<ProjectResponseDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public ProjectResponseDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return convertToResponseDTO(project);
    }

    @org.springframework.transaction.annotation.Transactional
    public ProjectResponseDTO updateProject(Long projectId, Long userId, ProjectRequestDTO projectRequestDTO) {
        // Retrieve the project from the repository
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Check if the user is the owner of the project
        if (!project.getUser().getId().equals(userId)) {
            throw new RuntimeException("User is not authorized to update this project");
        }

        // Update project details
        project.setProjectName(projectRequestDTO.getProjectName());
        project.setDescription(projectRequestDTO.getDescription());

        // Check if there are any new members to be added
        if (projectRequestDTO.getMemberIds() != null && !projectRequestDTO.getMemberIds().isEmpty()) {
            // Delegate the task of updating project members to the project member service
            projectMemberService.updateProjectMembers(project, projectRequestDTO.getMemberIds());
        }

        // Save the updated project in the repository
        project = projectRepository.save(project);

        return convertToResponseDTO(project);
    }
    @org.springframework.transaction.annotation.Transactional
    public void deleteProject(Long projectId, Long userId) {
        // Retrieve the project from the repository
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Check if the user is the owner of the project
        if (!project.getUser().getId().equals(userId)) {
            throw new RuntimeException("User is not authorized to delete this project");
        }

        // Delete the project
        projectRepository.delete(project);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getProjectsForMember(Long userId) {
        List<Long> projectIds = projectMemberService.getProjectIdsForMember(userId);
        List<Project> projects = projectRepository.findByIdIn(projectIds);
        return convertProjectsToDTOs(projects);
    }

    private List<ProjectResponseDTO> convertProjectsToDTOs(List<Project> projects) {
        return projects.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private void addProjectMembers(Project project, List<Long> memberIds) {
        List<ProjectMember> projectMembers = memberIds.stream()
                .map(memberId -> {
                    // Search and retrieve members by member ID
                    Member member = memberRepository.findById(memberId)
                            .orElseThrow(() -> new RuntimeException("Member not found"));
                    // Create and set project members
                    ProjectMember projectMember = new ProjectMember();
                    projectMember.setUser(member);
                    projectMember.setProject(project);
                    return projectMember;
                })
                .collect(Collectors.toList());

        // Save the project members in the repository
        projectMemberRepository.saveAll(projectMembers);
    }

    private ProjectResponseDTO convertToResponseDTO(Project project) {
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setId(project.getId());
        dto.setUser(project.getUser());
        dto.setProjectName(project.getProjectName());
        dto.setDescription(project.getDescription());
        dto.setCreatedAt(project.getCreatedAt());
        dto.setOwner(project.getOwner());

        List<ProjectMemberResponseDTO> memberDTOs = project.getProjectMembers().stream()
                .map(this::convertProjectMemberToDTO)
                .collect(Collectors.toList());
        dto.setProjectMembers(memberDTOs);

        return dto;
    }

    private ProjectMemberResponseDTO convertProjectMemberToDTO(ProjectMember projectMember) {
        ProjectMemberResponseDTO dto = new ProjectMemberResponseDTO();
        dto.setId(projectMember.getId());
        dto.setUserId(projectMember.getUser().getId());
        dto.setProjectId(projectMember.getProject().getId());
        return dto;
    }
}
