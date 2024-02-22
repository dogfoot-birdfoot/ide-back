package com.ide.back.domain.chat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatMessage is a Querydsl query type for ChatMessage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatMessage extends EntityPathBase<ChatMessage> {

    private static final long serialVersionUID = -1480915123L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatMessage chatMessage = new QChatMessage("chatMessage");

    public final QBaseTime _super = new QBaseTime(this);

    public final NumberPath<Long> chatMessageId = createNumber("chatMessageId", Long.class);

    public final QChatRoom chatroom;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath message = createString("message");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final com.ide.back.domain.QMemberEntity user;

    public QChatMessage(String variable) {
        this(ChatMessage.class, forVariable(variable), INITS);
    }

    public QChatMessage(Path<? extends ChatMessage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatMessage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatMessage(PathMetadata metadata, PathInits inits) {
        this(ChatMessage.class, metadata, inits);
    }

    public QChatMessage(Class<? extends ChatMessage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatroom = inits.isInitialized("chatroom") ? new QChatRoom(forProperty("chatroom"), inits.get("chatroom")) : null;
        this.user = inits.isInitialized("user") ? new com.ide.back.domain.QMemberEntity(forProperty("user")) : null;
    }

}

