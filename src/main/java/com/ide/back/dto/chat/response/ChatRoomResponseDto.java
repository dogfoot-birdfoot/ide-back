package com.ide.back.dto.chat.response;

import com.ide.back.domain.chat.ChatRoom;
import com.ide.back.dto.chat.request.ChatRoomRequestDto;

import java.time.format.DateTimeFormatter;

public class ChatRoomResponseDto {
    private Long id;
    private String roomName;
    private String createdDate;
    private String updatedDate;

    public ChatRoomResponseDto(ChatRoom entity){
        this.id = entity.getId();
        this.roomName = entity.getRoomName();
        this.createdDate = entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        this.updatedDate = entity.getUpdatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }
}
