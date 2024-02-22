package com.ide.back.domain.chat;

import com.ide.back.domain.Member;
import com.ide.back.domain.MemberEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "chat_message")
public class ChatMessage extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMessageId;

    @Column(nullable = false, length = 255)
    private String message;

    //BaseTime 상속으로 만든시간 수정시간 없어도 될듯합니다.
//    @Column(nullable = false)
//    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatRoom chatroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private MemberEntity user;

    @Builder
    public ChatMessage(MemberEntity user, String message, ChatRoom chatRoom){
        //this.user = user;
        this.message = message;
        this.chatroom = chatRoom;
    }
}
