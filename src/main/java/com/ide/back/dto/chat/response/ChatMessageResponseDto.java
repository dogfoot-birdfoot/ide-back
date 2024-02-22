package com.ide.back.dto.chat.response;

import com.ide.back.domain.Member;
import com.ide.back.domain.chat.ChatMessage;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ChatMessageResponseDto {
    private Long id;
    private Member user;
    private String message;
    private String createdDate;
    private String updatedDate;

    public ChatMessageResponseDto(ChatMessage entity) {
        this.id = entity.getChatMessageId();
        this.user = entity.getUser();
        this.message = entity.getMessage();
        this.createdDate = entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        this.updatedDate = entity.getUpdatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }
}
