package com.ide.back.repository;

import com.ide.back.domain.MemberEntity;
import com.ide.back.domain.Project;
import com.ide.back.domain.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember,Long> {
    List<ProjectMember> findAllByProject(Project project);

    Boolean findProjectMemberByProjectAndUser(Project project, MemberEntity user);
}
