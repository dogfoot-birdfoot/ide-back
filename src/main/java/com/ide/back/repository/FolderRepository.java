package com.ide.back.repository;

import com.ide.back.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findByUserIdAndProjectId(Long userId, Long projectId);
    Optional<Folder> findByIdAndUserId(Long id, Long userId);

    List<Folder> findByProjectId(Long projectId);
}
