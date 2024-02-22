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

    // 새로운 폴더 생성
    @PostMapping
    public ResponseEntity<FolderResponseDTO> createFolder(@PathVariable Long projectId,
                                                          @RequestBody FolderRequestDTO requestDTO,
                                                          @RequestParam Long userId) {
        //생성된 폴더 정보
        FolderResponseDTO createdFolder = folderService.createFolder(projectId, requestDTO, userId);
        return new ResponseEntity<>(createdFolder, HttpStatus.CREATED);
    }

    //프로젝트의 모든 폴더 가져오기
    @GetMapping
    public ResponseEntity<List<FolderResponseDTO>> getAllFoldersByProject(@PathVariable Long projectId,
                                                                          @RequestParam Long userId) {
        //프로젝트의 모든 폴더 가져옴
        List<FolderResponseDTO> folders = folderService.getAllFoldersByProject(projectId, userId);
        return ResponseEntity.ok(folders);
    }

    //특정 폴더 가져오기
    @GetMapping("/{folderId}")
    public ResponseEntity<FolderResponseDTO> getFolderById(@PathVariable Long projectId,
                                                           @PathVariable Long folderId,
                                                           @RequestParam Long userId) {
        FolderResponseDTO folder = folderService.getFolderById(folderId, userId);
        return ResponseEntity.ok(folder);
    }

    //특정 폴더 업데이트
    @PutMapping("/{folderId}")
    public ResponseEntity<FolderResponseDTO> updateFolder(@PathVariable Long projectId,
                                                          @PathVariable Long folderId,
                                                          @RequestBody FolderRequestDTO requestDTO,
                                                          @RequestParam Long userId) {
        FolderResponseDTO updatedFolder = folderService.updateFolder(folderId, requestDTO, userId);
        return ResponseEntity.ok(updatedFolder);
    }

    //특정 폴더 삭제
    @DeleteMapping("/{folderId}")
    public ResponseEntity<Void> deleteFolder(@PathVariable Long projectId,
                                             @PathVariable Long folderId,
                                             @RequestParam Long userId) {
        folderService.deleteFolder(folderId, userId);
        return ResponseEntity.noContent().build();
    }
}
