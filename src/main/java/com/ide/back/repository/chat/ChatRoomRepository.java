package com.ide.back.repository.chat;

import com.ide.back.domain.Project;
import com.ide.back.domain.chat.ChatRoom;
import org.springdoc.core.converters.models.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findByRoomName(String roomName);

    List<ChatRoom> findAllByProject(Project project);

    //기본정렬, 일치
    List<ChatRoom> findAllByRoomName(String roomName);

    //조건정렬, 일치
    List<ChatRoom> findAllByRoomName(String roomName, Sort sort);

    //기본정렬, 포함
    List<ChatRoom> findAllByRoomNameContaining(String roomName);

    //조건정렬, 포함
    List<ChatRoom> findAllByRoomNameContaining(String roomName, Sort sort);
}
