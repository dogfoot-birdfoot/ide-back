package com.ide.back.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFolder is a Querydsl query type for Folder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFolder extends EntityPathBase<Folder> {

    private static final long serialVersionUID = 841699586L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFolder folder = new QFolder("folder");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final ListPath<File, QFile> files = this.<File, QFile>createList("files", File.class, QFile.class, PathInits.DIRECT2);

    public final StringPath folderName = createString("folderName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QFolder parentFolder;

    public final QProject project;

    public final ListPath<Folder, QFolder> subFolders = this.<Folder, QFolder>createList("subFolders", Folder.class, QFolder.class, PathInits.DIRECT2);

    public final QMember user;

    public QFolder(String variable) {
        this(Folder.class, forVariable(variable), INITS);
    }

    public QFolder(Path<? extends Folder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFolder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFolder(PathMetadata metadata, PathInits inits) {
        this(Folder.class, metadata, inits);
    }

    public QFolder(Class<? extends Folder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parentFolder = inits.isInitialized("parentFolder") ? new QFolder(forProperty("parentFolder"), inits.get("parentFolder")) : null;
        this.project = inits.isInitialized("project") ? new QProject(forProperty("project"), inits.get("project")) : null;
        this.user = inits.isInitialized("user") ? new QMember(forProperty("user")) : null;
    }

}

