package com.ide.back.repository.chat;

import com.ide.back.domain.chat.ChatMessage;
import com.ide.back.domain.chat.ChatRoom;
import org.springdoc.core.converters.models.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByChatroom(ChatRoom chatRoom);

    List<ChatMessage> findAllByChatroom(ChatRoom chatRoom, Sort sort);

    List<ChatMessage> findAllByMessageContaining(String message);

    List<ChatMessage> findAllByMessageContaining(String Message, Sort sort);
}
