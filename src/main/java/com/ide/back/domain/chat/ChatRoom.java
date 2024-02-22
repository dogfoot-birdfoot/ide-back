package com.ide.back.domain.chat;

import com.ide.back.domain.Project;
import com.ide.back.dto.chat.request.ChatMessageRequestDto;
import com.ide.back.dto.chat.request.ChatRoomRequestDto;
import com.ide.back.service.chat.ChatMessageService;
import com.ide.back.service.chat.ChatRoomService;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Table(name = "chat_room")
@NoArgsConstructor
public class ChatRoom extends BaseTime {
    private static Set<WebSocketSession> sessions = new HashSet<>();
    @Id
    @Column(name = "chatroom_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    @Column(name = "room_name", nullable = false, length = 30)
    private String roomName;

    //BaseTime을 만들어서 상속시켜놔서 update date나 createdAt은 생략해도 될 것 같습니다.
//    @Column(name = "createdAt", nullable = false)
//    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToMany(mappedBy = "chatroom", cascade = CascadeType.REMOVE)
    private List<ChatMessage> chatMessageList;

    @Builder
    public ChatRoom(String roomName, Project project){
        this.roomName = roomName;
        this.project = project;
    }

    public Long update(ChatRoomRequestDto requestDto){
        this.roomName = requestDto.getRoomName();
        return this.chatRoomId;
    }
}
