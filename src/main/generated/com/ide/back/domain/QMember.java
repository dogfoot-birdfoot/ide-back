package com.ide.back.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1032896302L;

    public static final QMember member = new QMember("member1");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final ListPath<File, QFile> files = this.<File, QFile>createList("files", File.class, QFile.class, PathInits.DIRECT2);

    public final ListPath<Folder, QFolder> folders = this.<Folder, QFolder>createList("folders", Folder.class, QFolder.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<ProjectMember, QProjectMember> projectMembers = this.<ProjectMember, QProjectMember>createList("projectMembers", ProjectMember.class, QProjectMember.class, PathInits.DIRECT2);

    public final ListPath<Project, QProject> projects = this.<Project, QProject>createList("projects", Project.class, QProject.class, PathInits.DIRECT2);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

