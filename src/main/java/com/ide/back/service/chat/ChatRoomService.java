package com.ide.back.service.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ide.back.config.websocket.chat.ChattingRoom;
import com.ide.back.domain.Member;
import com.ide.back.domain.Project;
import com.ide.back.domain.chat.ChatMessage;
import com.ide.back.domain.chat.ChatRoom;
import com.ide.back.dto.chat.request.ChatMessageRequestDto;
import com.ide.back.dto.chat.request.ChatRoomRequestDto;
import com.ide.back.dto.chat.response.ChatRoomResponseDto;
import com.ide.back.exception.ApiException;
import com.ide.back.repository.MemberRepository;
import com.ide.back.repository.ProjectMemberRepository;
import com.ide.back.repository.ProjectRepository;
import com.ide.back.repository.chat.ChatRoomRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ObjectMapper mapper;
    private Map<Long, ChattingRoom> chattingRooms;
    private Map<WebSocketSession, Long> sessionMap;

    @PostConstruct
    private void init() {
        chattingRooms = new LinkedHashMap<>();
        sessionMap = new LinkedHashMap<>();
    }

    public ChattingRoom findRoomById(Long roomId) {
        return chattingRooms.get(roomId);
    }

    public void addSession(WebSocketSession webSocketSession, Long roomId){
        this.sessionMap.put(webSocketSession, roomId);
    }

    public void deleteSession(WebSocketSession webSocketSession){
        Long key = this.sessionMap.get(webSocketSession);
        ChattingRoom chattingRoom = this.chattingRooms.get(key);

        chattingRoom.deleteSession(webSocketSession);
        this.sessionMap.remove(webSocketSession);

        return;
    }

    //조회
    @Transactional
    public ChatRoomResponseDto findById(final Long id){
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
        Member member = this.memberRepository.findById(memberId).orElseThrow(
                ()-> new IllegalArgumentException("해당 member가 존재하지 않습니다 memberId = " +memberId)
        );
        Project project = this.projectRepository.findById(projectId).orElseThrow(
                ()-> new IllegalArgumentException("해당 project가 존재하지 않습니다 projectId = " + projectId)
        );
        boolean projectMember= this.projectMemberRepository.existsByUserAndProject(member, project);
        ChatRoomRequestDto requestDto = new ChatRoomRequestDto(roomName, project);

        //핸들러에서 채팅방 세션 연결하는거 추가 필요
        if (projectMember){
            ChatRoom chatRoom = requestDto.toEntity();
            this.chatRoomRepository.save(requestDto.toEntity());
            ChattingRoom chattingRoom = ChattingRoom.builder()
                    .id(chatRoom.getChatRoomId())
                    .roomName(chatRoom.getRoomName())
                    .build();
            chattingRooms.put(chattingRoom.getId(), chattingRoom);
            return chatRoom.getChatRoomId();
        }else return (long) -1;
    }

    @Transactional
    public void saveMessage(ChatMessageRequestDto chatMessageRequestDto){
        Long memberId = chatMessageRequestDto.getUser().getId();
        Member member = this.memberRepository.findById(memberId).orElseThrow(
                ()-> new IllegalArgumentException("해당 member가 존재하지 않습니다. memberId = "+memberId)
        );
        Long chatRoomId = chatMessageRequestDto.getChatRoom().getChatRoomId();
        ChatRoom chatRoom = this.chatRoomRepository.findById(chatRoomId).orElseThrow(
                ()-> new IllegalArgumentException("해당 chattingRoom이 존재하지 않습니다. chatRoomId = "+chatRoomId)
        );
        ChatMessage chatMessage = new ChatMessage(member, chatMessageRequestDto.getMessage(), chatRoom);
    }
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            throw new ApiException(HttpStatus.BAD_GATEWAY, "입출력 오류");
        }
    }


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
