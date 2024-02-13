create table account
(
    id        bigint(20)   not null auto_increment,
    nickname  varchar(30)  not null,
    email     varchar(50)  not null,
    password  varchar(255) not null,
    createdAt datetime     not null,
    deletedAt datetime,

    primary key (id)
);

create table project
(
    id          bigint(20)   not null auto_increment,
    name        varchar(50)  not null,
    description varchar(255) not null,
    createdAt   datetime     not null,
    updatedAt   datetime     not null,
    deletedAt   datetime     not null,
    owner       bigint(20)   not null,
    author      bigint(20)   not null,

    key ix_name (name),
    key ix_owner (owner),
    primary key (id)
);


create table folder
(
    id          bigint(20)   not null auto_increment,
    name        varchar(50)  not null,
    createdAt   datetime     not null,
    updatedAt   datetime     not null,
    deletedAt   datetime     not null,
    author      bigint(20)   not null,
    parent_id   bigint(20)   not null,
    project_id  bigint(20)   not null,

    primary key (id)
);

create table file
(
    id          bigint(20)  not null auto_increment,
    origin_name varchar(50) not null,
    s3_name     binary(16)  not null,
    createdAt   datetime    not null,
    updatedAt   datetime    not null,
    deletedAt   datetime    not null,
    author      bigint(20)  not null,
    folder_id   bigint(20)  not null,
    project_id  bigint(20)  not null,

    primary key (id)
);
