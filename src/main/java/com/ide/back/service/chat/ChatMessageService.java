package com.ide.back.service.chat;

import com.ide.back.domain.chat.ChatMessage;
import com.ide.back.domain.chat.ChatRoom;
import com.ide.back.dto.chat.request.ChatMessageRequestDto;
import com.ide.back.dto.chat.response.ChatMessageResponseDto;
import com.ide.back.repository.chat.ChatMessageRepository;
import com.ide.back.repository.chat.ChatRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatMessageService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

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
    public Long save(final Long chatRoomId, final ChatMessageRequestDto requestDto){
        ChatRoom chatRoomEntity = this.chatRoomRepository.findById(chatRoomId).orElseThrow(
                ()-> new IllegalArgumentException("해당 chatRoom이 존재하지 않습니다. chatRoomId = "+ chatRoomId)
        );
        return this.chatMessageRepository.save(requestDto.toEntity()).getId();
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
