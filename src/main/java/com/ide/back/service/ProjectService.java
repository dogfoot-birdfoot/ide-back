package com.ide.back.service;

import com.ide.back.domain.Member;
import com.ide.back.domain.Project;
import com.ide.back.dto.ProjectRequestDTO;
import com.ide.back.dto.ProjectResponseDTO;
import com.ide.back.exception.ProjectNotFoundException;
import com.ide.back.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, MemberRepository memberRepository) {
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
    }

    //프로젝트 생성
    public ProjectResponseDTO createProject(ProjectRequestDTO dto) {
        Member member = memberRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Project project = dto.toEntity(member);
        project = projectRepository.save(project);
        return convertToResponseDTO(project);
    }

    //프로젝트 검색
    public ProjectResponseDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return convertToResponseDTO(project);
    }

    //모든 프로젝트 검색
    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    //프로젝트 수정
    public ProjectResponseDTO updateProject(Long id, ProjectRequestDTO projectRequestDTO) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        if (projectRequestDTO.getUserId() != null) {
            Member user = memberRepository.findById(projectRequestDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            project.setUser(user);
        }
        project.updateProjectFromDTO(projectRequestDTO);
        project = projectRepository.save(project);
        return convertToResponseDTO(project);
    }

    @Transactional
    //프로젝트 삭제
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }


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
