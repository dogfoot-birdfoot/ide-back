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
    public FolderService(FolderRepository folderRepository, ProjectRepository projectRepository,
                         MemberRepository memberRepository, ProjectMemberService projectMemberService) {
        this.folderRepository = folderRepository;
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
        this.projectMemberService = projectMemberService;
    }

    // 폴더 생성 메소드
    public FolderResponseDTO createFolder(Long projectId, FolderRequestDTO requestDTO) {
        // 프로젝트 멤버인지 확인
        if (!projectMemberService.isMemberOfProject(requestDTO.getUserId(), projectId)) {
            throw new RuntimeException("User is not a member of the project");
        }

        // 사용자 조회
        Member user = memberRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 프로젝트 조회
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        // 새 폴더 객체 생성 및 속성 설정
        Folder folder = new Folder();
        folder.setFolderName(requestDTO.getFolderName());
        folder.setProject(project);
        folder.setUser(user);

        // 부모 폴더 설정 (있을 경우)
        if (requestDTO.getParentId() != null) {
            Folder parentFolder = folderRepository.findById(requestDTO.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent folder not found"));
            folder.setParentFolder(parentFolder);
        }

        // 폴더 저장 및 응답 객체로 변환
        folder = folderRepository.save(folder);
        return convertToFolderResponseDTO(folder);
    }

    // 프로젝트별 모든 폴더 조회 메소드
    public List<FolderResponseDTO> getAllFoldersByProject(Long projectId, Long userId) {
        // 프로젝트 멤버인지 확인
        if (!projectMemberService.isMemberOfProject(userId, projectId)) {
            throw new RuntimeException("User is not a member of the project");
        }

        // 프로젝트 ID로 폴더 리스트 조회
        List<Folder> folders = folderRepository.findByProjectId(projectId);
        return folders.stream().map(this::convertToFolderResponseDTO).collect(Collectors.toList());
    }

    // 특정 폴더 조회 메소드
    public FolderResponseDTO getFolderById(Long folderId, Long userId) {
        // 폴더 조회
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found"));

        // 프로젝트 멤버인지 확인
        if (!projectMemberService.isMemberOfProject(userId, folder.getProject().getId())) {
            throw new RuntimeException("User is not a member of this project");
        }

        // 응답 객체로 변환
        return convertToFolderResponseDTO(folder);
    }

    // 폴더 정보 업데이트 메소드
    public FolderResponseDTO updateFolder(Long folderId, FolderRequestDTO requestDTO) {
        // 프로젝트 멤버인지 확인
        if (!projectMemberService.isMemberOfProject(requestDTO.getUserId(), requestDTO.getProjectId())) {
            throw new RuntimeException("User is not a member of the project");
        }

        // 폴더 조회
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found"));

        // 폴더 이름 업데이트
        folder.setFolderName(requestDTO.getFolderName());

        // 부모 폴더 업데이트 (제공된 경우)
        if (requestDTO.getParentId() != null) {
            Folder parentFolder = folderRepository.findById(requestDTO.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent folder not found"));
            folder.setParentFolder(parentFolder);
        }

        // 폴더 저장 및 응답 객체로 변환
        folder = folderRepository.save(folder);
        return convertToFolderResponseDTO(folder);
    }

    // 폴더 삭제 메소드
    public void deleteFolder(Long folderId, Long userId) {
        // 폴더 조회
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found"));

        // 프로젝트 멤버인지 확인
        if (!projectMemberService.isMemberOfProject(userId, folder.getProject().getId())) {
            throw new RuntimeException("User is not a member of this project");
        }

        // 폴더 삭제
        folderRepository.delete(folder);
    }

    // Folder 엔티티를 FolderResponseDTO로 변환하는 메소드
    private FolderResponseDTO convertToFolderResponseDTO(Folder folder) {
        FolderResponseDTO dto = new FolderResponseDTO();
        dto.setId(folder.getId());
        dto.setFolderName(folder.getFolderName());
        dto.setCreateAt(folder.getCreatedAt());
        dto.setParentId(folder.getParentFolder() != null ? folder.getParentFolder().getId() : null);
        dto.setUserId(folder.getUser() != null ? folder.getUser().getId() : null);
        dto.setProjectId(folder.getProject() != null ? folder.getProject().getId() : null);
        return dto;
    }

}
