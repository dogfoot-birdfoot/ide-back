package com.ide.back.service;

import com.ide.back.domain.Project;
import com.ide.back.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    //프로젝트 생성
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    //프로젝트 검색
    public Optional<Project> getProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }

    //모든 프로젝트 검색
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    //프로젝트 수정
    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }

    //프로젝트 삭제
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

}
