package com.ide.back.service.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ide.back.domain.MemberEntity;
import com.ide.back.domain.chat.ChatMessage;
import com.ide.back.domain.chat.ChatRoom;
import com.ide.back.dto.chat.request.ChatMessageRequestDto;
import com.ide.back.dto.chat.response.ChatMessageResponseDto;
import com.ide.back.exception.ApiException;
import com.ide.back.repository.MemberRepository;
import com.ide.back.repository.chat.ChatMessageRepository;
import com.ide.back.repository.chat.ChatRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatMessageService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final ObjectMapper mapper;

    //조회
    @Transactional
    public ChatMessageResponseDto findById(final Long chatMessageId){
        ChatMessage chatMessageEntity = this.chatMessageRepository.findById(chatMessageId).orElseThrow(
                ()-> new IllegalArgumentException("해당 ChatMessage가 존재하지 않습니다. chatMessageId = "+ chatMessageId)
        );
        return new ChatMessageResponseDto(chatMessageEntity);
    }

    //채팅방 메시지 최신순 조회
    @Transactional
    public List<ChatMessageResponseDto> findAllByChatRoomIdDesc(final Long chatRoomId){
        ChatRoom chatRoomEntity = this.chatRoomRepository.findById(chatRoomId).orElseThrow(
                ()-> new IllegalArgumentException("해당 charRoom이 존재하지 않습니다. chatRoomId = "+ chatRoomId)
        );
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<ChatMessage> chatMessageList = this.chatMessageRepository.findAllByChatroom(chatRoomEntity, sort);
        return chatMessageList.stream().map(ChatMessageResponseDto::new).collect(Collectors.toList());
    }

    //생성
    @Transactional
    public void saveMessage(ChatMessageRequestDto chatMessageRequestDto){
        Long memberId = chatMessageRequestDto.getUser().getId();
        MemberEntity member = this.memberRepository.findById(memberId).orElseThrow(
                ()-> new IllegalArgumentException("해당 member가 존재하지 않습니다. memberId = "+memberId)
        );
        Long chatRoomId = chatMessageRequestDto.getChatRoom().getChatRoomId();
        ChatRoom chatRoom = this.chatRoomRepository.findById(chatRoomId).orElseThrow(
                ()-> new IllegalArgumentException("해당 chattingRoom이 존재하지 않습니다. chatRoomId = "+chatRoomId)
        );
        ChatMessage chatMessage = new ChatMessage(member, chatMessageRequestDto.getMessage(), chatRoom);
    }

    //메세지 전송
    public <T> void sendMessage(WebSocketSession session, T message){
        try{
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        }catch(IOException e){
            throw  new ApiException(HttpStatus.BAD_GATEWAY, "입출력 오류");
        }
    }

    //삭제
    @Transactional
    public void delete(final Long chatMessageId){
        ChatMessage chatMessageEntity = this.chatMessageRepository.findById(chatMessageId).orElseThrow(
                ()-> new IllegalArgumentException("해당 chatMessage가 존재하지 않습니다. chatMessageId = " + chatMessageId)
        );
        this.chatMessageRepository.delete(chatMessageEntity);
    }
}
