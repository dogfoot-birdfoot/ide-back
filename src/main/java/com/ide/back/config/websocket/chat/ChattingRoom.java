package com.ide.back.config.websocket.chat;

import com.ide.back.domain.Project;
import com.ide.back.dto.chat.request.ChatMessageRequestDto;
import com.ide.back.service.chat.ChatMessageService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class ChattingRoom {
    private Long id;
    private String roomName;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChattingRoom(Long id, String roomName){
        this.id = id;
        this.roomName = roomName;
    }

    public void handlerActions(WebSocketSession session, ChatMessageRequestDto chatMessageRequestDto, ChatMessageService chatMessageService){
        if (chatMessageRequestDto.getType().equals(ChatMessageRequestDto.MessageType.ENTER)){
            sessions.add(session); // 채팅방에 입장하는 경우 참여자의 세선을 추가
        }
        chatMessageService.saveMessage(chatMessageRequestDto);
        sendMessage(chatMessageRequestDto, chatMessageService);
    }

    private <T> void sendMessage(T message, ChatMessageService chatMessageService){
        //채팅방에 참여중인 모든 사람에게 전송
        sessions.parallelStream()
                .forEach(session -> chatMessageService.sendMessage(session, message));
    }

    public void deleteSession(WebSocketSession webSocketSession){
        this.sessions.remove(webSocketSession);
    }


}
