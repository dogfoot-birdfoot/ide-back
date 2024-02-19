package com.ide.back.dto.chat.request;

import com.ide.back.domain.Member;
import com.ide.back.domain.chat.ChatMessage;
import com.ide.back.domain.chat.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageRequestDto {
    private Member user;
    private String message;
    private ChatRoom chatRoom;

    @Builder
    public ChatMessageRequestDto(Member user, String message, ChatRoom chatRoom){
        this.user = user;
        this.message = message;
        this.chatRoom = chatRoom;
    }

    public ChatMessage toEntity(){
        return ChatMessage.builder()
                .user(this.user)
                .message(this.message)
                .chatRoom(this.chatRoom)
                .build();
    }
}
