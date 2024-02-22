package com.ide.back.dto.chat.request;

import com.ide.back.domain.Project;
import com.ide.back.domain.chat.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomRequestDto {
    private String roomName;
    private Project project;

    @Builder
    public ChatRoomRequestDto(String roomName, Project project){
        this.roomName = roomName;
        this.project = project;
    }

    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .roomName(this.roomName)
                .project(this.project)
                .build();
    }
}
