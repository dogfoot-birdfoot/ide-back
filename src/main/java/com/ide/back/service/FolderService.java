package com.ide.back.service;

import com.ide.back.domain.Folder;
import com.ide.back.domain.Member;
import com.ide.back.domain.Project;
import com.ide.back.dto.FolderRequestDTO;
import com.ide.back.dto.FolderResponseDTO;
import com.ide.back.repository.FolderRepository;
import com.ide.back.repository.MemberRepository;
import com.ide.back.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FolderService {
    private final FolderRepository folderRepository;
    private final ProjectRepository projectRepository;

    private final MemberRepository memberRepository;
    private final ProjectMemberService projectMemberService;

    @Autowired
    public FolderService(FolderRepository folderRepository, ProjectRepository projectRepository, MemberRepository memberRepository, ProjectMemberService projectMemberService) {
        this.folderRepository = folderRepository;
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
        this.projectMemberService = projectMemberService;
    }

    // 폴더 생성 메서드
    public FolderResponseDTO createFolder(Long projectId, FolderRequestDTO requestDTO, Long userId) {
        //프로젝트 멤버인지 확인
        if (!projectMemberService.isMemberOfProject(userId, projectId)) {
            throw new RuntimeException("User is not a member of the project");
        }
        // 유저 찾기
        Member user = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found")); // 올바른 Member 찾기

        // 프로젝트 찾기
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        Folder folder = new Folder();
        folder.setFolderName(requestDTO.getFolderName());
        folder.setProject(project);
        folder.setUser(user);

        // 부모 폴더 처리
        if (requestDTO.getParentId() != null) {
            Folder parentFolder = folderRepository.findById(requestDTO.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent folder not found"));
            folder.setParentFolder(parentFolder);
        }

        folder = folderRepository.save(folder);
        return convertToFolderResponseDTO(folder);
    }

    // 프로젝트의 모든 폴더 가져오기
    public List<FolderResponseDTO> getAllFoldersByProject(Long projectId, Long userId) {
        // 프로젝트의 멤버인지 확인
        if (!projectMemberService.isMemberOfProject(userId, projectId)) {
            throw new RuntimeException("User is not a member of the project");
        }

        // 프로젝트의 모든 폴더 가져오기
        List<Folder> folders = folderRepository.findByProjectId(projectId);
        return folders.stream().map(this::convertToFolderResponseDTO).collect(Collectors.toList());
    }

    // 폴더 ID로 폴더 가져오기
    public FolderResponseDTO getFolderById(Long folderId, Long userId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found"));

        // 프로젝트의 멤버인지 확인
        if (!projectMemberService.isMemberOfProject(userId, folder.getProject().getId())) {
            throw new RuntimeException("User is not a member of this project");
        }

        return convertToFolderResponseDTO(folder);
    }

    // 폴더 업데이트
    public FolderResponseDTO updateFolder(Long folderId, FolderRequestDTO requestDTO, Long userId) {
        // 프로젝트의 멤버인지 확인
        if (!projectMemberService.isMemberOfProject(userId, requestDTO.getProjectId())) {
            throw new RuntimeException("User is not a member of the project");
        }

        // 폴더 찾기
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found"));
        folder.setFolderName(requestDTO.getFolderName());

        // 선택적으로 부모 폴더 업데이트
        if (requestDTO.getParentId() != null) {
            Folder parentFolder = folderRepository.findById(requestDTO.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent folder not found"));
            folder.setParentFolder(parentFolder);
        }

        folder = folderRepository.save(folder);
        return convertToFolderResponseDTO(folder);
    }

    // 폴더 삭제
    public void deleteFolder(Long folderId, Long userId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found"));

        // 프로젝트의 멤버인지 확인
        if (!projectMemberService.isMemberOfProject(userId, folder.getProject().getId())) {
            throw new RuntimeException("User is not a member of this project");
        }

        folderRepository.delete(folder);
    }

    //DTO로 변환
    private FolderResponseDTO convertToFolderResponseDTO(Folder folder) {
        FolderResponseDTO dto = new FolderResponseDTO();
        dto.setId(folder.getId());
        dto.setFolderName(folder.getFolderName());
        dto.setParentId(folder.getParentFolder() != null ? folder.getParentFolder().getId() : null);
        dto.setUserId(folder.getUser() != null ? folder.getUser().getId() : null);
        dto.setProjectId(folder.getProject().getId());
        return dto;
    }

}
