package com.ide.back.repository;

import com.ide.back.domain.Member;
import com.ide.back.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUser(Member user);
    List<Project> findByIdIn(List<Long> projectIds);
}
