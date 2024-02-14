CREATE TABLE member (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        nickname VARCHAR(30) NOT NULL,
                        email VARCHAR(50) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        created_at DATETIME NOT NULL,
                        deleted_at DATETIME
);

CREATE TABLE project (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         user_id BIGINT NOT NULL,
                         project_name VARCHAR(50) NOT NULL,
                         description VARCHAR(255) NOT NULL,
                         created_at DATETIME NOT NULL,
                         deleted_at DATETIME,
                         owner VARCHAR(30) NOT NULL,
                         author VARCHAR(30) NOT NULL
);



CREATE TABLE folder (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        folder_name VARCHAR(255) NOT NULL,
                        created_at DATETIME NOT NULL,
                        deleted_at DATETIME,
                        parent_id BIGINT,
                        user_id BIGINT NOT NULL,
                        project_id BIGINT NOT NULL
);

CREATE TABLE file (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      file_name VARCHAR(50) NOT NULL,
                      cloud_file_name VARCHAR(255) NOT NULL,
                      file_content VARCHAR(255),
                      created_at DATETIME NOT NULL,
                      deleted_at DATETIME,
                      user_id BIGINT NOT NULL,
                      folder_id BIGINT NOT NULL,
                      project_id BIGINT NOT NULL
);

CREATE TABLE chat_message (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              message VARCHAR(255) NOT NULL,
                              created_at DATETIME NOT NULL,
                              chatroom_id BIGINT NOT NULL,
                              user_id BIGINT NOT NULL
);

CREATE TABLE chatroom (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          room_name VARCHAR(30) NOT NULL,
                          created_at DATETIME NOT NULL,
                          project_id BIGINT NOT NULL
);

CREATE TABLE project_member (
                                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                joined_at DATETIME NOT NULL,
                                user_id BIGINT NOT NULL,
                                project_id BIGINT NOT NULL
);
