package com.ide.back.repository;

import com.ide.back.domain.Member;
import com.ide.back.domain.Project;
import com.ide.back.domain.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    boolean existsByUserAndProject(Member user, Project project);
    List<ProjectMember> findByProjectId(Long projectId);

    @Query("SELECT pm FROM ProjectMember pm WHERE pm.project = :project AND pm.user = :member")
    ProjectMember findByProjectAndUser(@Param("project") Project project, @Param("member") Member member);

    List<Long> findProjectIdsByUserId(Long userId);

    List<ProjectMember> findByProject(Project project);
}
