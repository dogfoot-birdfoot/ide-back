package com.ide.back.repository;

import com.ide.back.domain.Member;
import com.ide.back.domain.Project;
import com.ide.back.domain.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    boolean existsByUserAndProject(Member user, Project project);
    List<ProjectMember> findByProjectId(Long projectId);
}
