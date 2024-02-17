package com.ide.back.dto.chat;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
    //메시지 타입 : 입장, 채팅
    public enum MessageType{
        ENTER, TALK
    }

    private MessageType messageType; //메세지 타입
    private Long chatRoomId; // 방번호
    private Long senderId; // 채팅 보낸 사람의 userId
    private String message; //메세지
}
