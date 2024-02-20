package com.ide.back.service.chat;

import com.ide.back.domain.MemberEntity;
import com.ide.back.domain.Project;
import com.ide.back.domain.chat.ChatMessage;
import com.ide.back.domain.chat.ChatRoom;
import com.ide.back.dto.chat.request.ChatMessageRequestDto;
import com.ide.back.dto.chat.request.ChatRoomRequestDto;
import com.ide.back.dto.chat.response.ChatRoomResponseDto;
import com.ide.back.repository.MemberRepository;
import com.ide.back.repository.ProjectMemberRepository;
import com.ide.back.repository.ProjectRepository;
import com.ide.back.repository.chat.ChatRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private Map<Long, ChatRoom> chatRooms;
    private Map<WebSocketSession, Long> sessionMap;

    //조회
    @Transactional
    public ChatRoomResponseDto findByIdDto(final Long id){
        ChatRoom entity = this.chatRoomRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 ChatRoom이 존재하지 않습니다. id = " + id)
        );
        return new ChatRoomResponseDto(entity);
    }
    @Transactional
    public ChatRoom findByIdEntity(final Long id){
        ChatRoom entity = this.chatRoomRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 ChatRoom이 존재하지 않습니다. id = " + id)
        );
        return entity;
    }

    //생성
    @Transactional
    public Long creatRoom(Long projectId, Long memberId, String roomName){
        MemberEntity member = this.memberRepository.findById(memberId).orElseThrow(
                ()-> new IllegalArgumentException("해당 member가 존재하지 않습니다 memberId = " +memberId)
        );
        Project project = this.projectRepository.findById(projectId).orElseThrow(
                ()-> new IllegalArgumentException("해당 project가 존재하지 않습니다 projectId = " + projectId)
        );
        boolean projectMember= this.projectMemberRepository.findProjectMemberByProjectAndUser(project, member);
        ChatRoomRequestDto requestDto = new ChatRoomRequestDto(roomName, project);

        //핸들러에서 채팅방 세션 연결하는거 추가 필요
        if (projectMember){
            return this.chatRoomRepository.save(requestDto.toEntity()).getId();
        }else return (long) -1;
    }

//    @Transactional
//    public void saveMessage(ChatMessageRequestDto chatMessageRequestDto){
//        Long memberId = chatMessageRequestDto.getUser().getId();
//        MemberEntity member = this.memberRepository.findById(memberId).orElseThrow(
//                ()-> new IllegalArgumentException("해당 member가 존재하지 않습니다. memberId = "+memberId)
//        );
//        Long chatRoomId = chatMessageRequestDto.getChatRoom().getId();
//        ChatRoom chatRoom = this.chatRoomRepository.findById(chatRoomId).orElseThrow(
//                ()-> new IllegalArgumentException("해당 chattingRoom이 존재하지 않습니다. chatRoomId = "+chatRoomId)
//        );
//        ChatMessage chatMessage = new ChatMessage(member, chatMessageRequestDto.getMessage(), chatRoom);
//    }
//
//

    //수정 - 채팅방 이름 밖에 못바꿈
    @Transactional
    public Long update(final Long id, ChatRoomRequestDto requestDto){
        ChatRoom entity = this.chatRoomRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 chatRoom이 존재하지 않습니다. id = " + id)
        );
        return entity.update(requestDto);
    }

    //삭제 -
    public void delete(final Long id){
        ChatRoom entity = this.chatRoomRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 chatRoom이 존재하지 않습니다. id = " + id)
        );
        this.chatRoomRepository.delete(entity);
    }
}
