package com.ide.back.dto.chat.response;

import com.ide.back.domain.Project;
import com.ide.back.domain.chat.ChatMessage;
import com.ide.back.domain.chat.ChatRoom;
import com.ide.back.dto.chat.request.ChatRoomRequestDto;

import java.time.format.DateTimeFormatter;

public class ChatRoomResponseDto {
    private Long id;
    private String roomName;
    private Project project;
    private String createdDate;
    private String updatedDate;

    public ChatRoomResponseDto(ChatRoom entity){
        this.id = entity.getId();
        this.project = entity.getProject();
        this.roomName = entity.getRoomName();
        this.createdDate = entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        this.updatedDate = entity.getUpdatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .roomName(this.roomName)
                .project(this.project)
                .build();
    }
}
