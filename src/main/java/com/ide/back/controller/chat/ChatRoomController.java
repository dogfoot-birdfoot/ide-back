package com.ide.back.controller.chat;


import com.ide.back.domain.chat.ChatRoom;
import com.ide.back.dto.chat.response.ChatRoomResponseDto;
import com.ide.back.service.chat.ChatMessageService;
import com.ide.back.service.chat.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    //채팅방 생성
    @PostMapping("/{projectId}/{memberId}/{roomName}")
    public ResponseEntity<Long> saveChatRoom(@PathVariable(name = "projectId") Long projectId,
                                                            @PathVariable(name = "memberId") Long memberId,
                                                            @PathVariable(name = "roomName") String roomName){
        Long chatRoomId = chatRoomService.creatRoom(projectId, memberId, roomName);
        if (chatRoomId == -1){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }else return new ResponseEntity<>(chatRoomId, HttpStatus.OK);
    }
}
