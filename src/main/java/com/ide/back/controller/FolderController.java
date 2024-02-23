package com.ide.back.controller;

import com.ide.back.dto.FolderRequestDTO;
import com.ide.back.dto.FolderResponseDTO;
import com.ide.back.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/folders")
public class FolderController {
    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    // 폴더 생성 API
    @PostMapping
    public ResponseEntity<FolderResponseDTO> createFolder(@PathVariable Long projectId,
                                                          @RequestBody FolderRequestDTO requestDTO) {
        // 폴더 서비스를 통해 폴더 생성 후 결과 반환
        FolderResponseDTO createdFolder = folderService.createFolder(projectId, requestDTO);
        return new ResponseEntity<>(createdFolder, HttpStatus.CREATED);
    }

    // 프로젝트 내 모든 폴더 조회 API
    @GetMapping
    public ResponseEntity<List<FolderResponseDTO>> getAllFoldersByProject(@PathVariable Long projectId,
                                                                          @RequestParam Long userId) {
        // 폴더 서비스를 통해 프로젝트의 모든 폴더 조회 후 결과 반환
        List<FolderResponseDTO> folders = folderService.getAllFoldersByProject(projectId, userId);
        return ResponseEntity.ok(folders);
    }

    // 특정 폴더 조회 API
    @GetMapping("/{folderId}")
    public ResponseEntity<FolderResponseDTO> getFolderById(@PathVariable Long projectId,
                                                           @PathVariable Long folderId,
                                                           @RequestParam Long userId) {
        // 폴더 서비스를 통해 특정 폴더 조회 후 결과 반환
        FolderResponseDTO folder = folderService.getFolderById(folderId, userId);
        return ResponseEntity.ok(folder);
    }

    // 폴더 정보 업데이트 API
    @PutMapping("/{folderId}")
    public ResponseEntity<FolderResponseDTO> updateFolder(@PathVariable Long projectId,
                                                          @PathVariable Long folderId,
                                                          @RequestBody FolderRequestDTO requestDTO) {
        // 폴더 서비스를 통해 폴더 정보 업데이트 후 결과 반환
        FolderResponseDTO updatedFolder = folderService.updateFolder(folderId, requestDTO);
        return ResponseEntity.ok(updatedFolder);
    }

    // 폴더 삭제 API
    @DeleteMapping("/{folderId}")
    public ResponseEntity<Void> deleteFolder(@PathVariable Long projectId,
                                             @PathVariable Long folderId,
                                             @RequestParam Long userId) {
        // 폴더 서비스를 통해 폴더 삭제
        folderService.deleteFolder(folderId, userId);
        return ResponseEntity.noContent().build();
    }
}
