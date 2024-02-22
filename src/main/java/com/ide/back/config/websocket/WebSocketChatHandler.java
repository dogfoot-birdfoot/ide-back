package com.ide.back.config.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ide.back.config.websocket.chat.ChattingRoom;
import com.ide.back.domain.chat.ChatRoom;
import com.ide.back.dto.chat.request.ChatMessageRequestDto;
import com.ide.back.service.chat.ChatMessageService;
import com.ide.back.service.chat.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ChatMessageRequestDto chatMessageRequestDto = mapper.readValue(payload, ChatMessageRequestDto.class);
        ChattingRoom chattingRoom = chatRoomService.findRoomById(chatMessageRequestDto.getChatRoom().getChatRoomId());
        chattingRoom.handlerActions(session, chatMessageRequestDto, chatMessageService);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        chatRoomService.deleteSession(session);
    }
}