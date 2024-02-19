package com.ide.back.service.chat;

import com.ide.back.domain.chat.ChatRoom;
import com.ide.back.dto.chat.request.ChatRoomRequestDto;
import com.ide.back.dto.chat.response.ChatRoomResponseDto;
import com.ide.back.repository.chat.ChatRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    //조회
    @Transactional
    public ChatRoomResponseDto findById(final Long id){
        ChatRoom entity = this.chatRoomRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 ChatRoom이 존재하지 않습니다. id = " + id)
        );
        return new ChatRoomResponseDto(entity);
    }

    //생성
    @Transactional
    public Long save(final ChatRoomRequestDto requestDto){
        return this.chatRoomRepository.save(requestDto.toEntity()).getId();
    }

    //수정
    @Transactional
    public Long update(final Long id, ChatRoomRequestDto requestDto){
        ChatRoom entity = this.chatRoomRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 chatRoom이 존재하지 않습니다. id = " + id)
        );
        return entity.update(requestDto);
    }

    //삭제
    public void delete(final Long id){
        ChatRoom entity = this.chatRoomRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 chatRoom이 존재하지 않습니다. id = " + id)
        );
        this.chatRoomRepository.delete(entity);
    }
}
