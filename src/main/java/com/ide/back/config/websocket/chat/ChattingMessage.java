package com.ide.back.config.websocket.chat;

import lombok.Data;

@Data
public class ChattingMessage {
    public enum MessageType{
        ENTER, TALK
    }

    private MessageType type;
    private Long id;
    private String sender;
    private String message;
}
