CREATE TABLE folder (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        folder_name VARCHAR(255) NOT NULL,
                        created_at DATETIME NOT NULL,
                        deleted_at DATETIME,
                        parent_id BIGINT,
                        user_id BIGINT NOT NULL,
                        project_id BIGINT NOT NULL
);

CREATE TABLE member (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nickname VARCHAR(50) UNIQUE NOT NULL,
                        email VARCHAR(50) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        created_at DATETIME NOT NULL,
                        deleted_at DATETIME
);

CREATE TABLE project (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         user_id BIGINT NOT NULL,
                         project_name VARCHAR(50) NOT NULL,
                         description VARCHAR(255) NOT NULL,
                         created_at DATETIME NOT NULL,
                         deleted_at DATETIME,
                         owner VARCHAR(30) NOT NULL,
                         author VARCHAR(30) NOT NULL
);

CREATE TABLE project_member (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                joined_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                user_id BIGINT NOT NULL,
                                project_id BIGINT NOT NULL
);

CREATE TABLE refresh_token (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               token VARCHAR(255) NOT NULL,
                               email VARCHAR(50) NOT NULL
);

CREATE TABLE chat_message (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              message VARCHAR(255) NOT NULL,
                              created_at DATETIME NOT NULL,
                              chatroom_id BIGINT NOT NULL,
                              user_id BIGINT NOT NULL
);

CREATE TABLE chatroom (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          room_name VARCHAR(30) NOT NULL,
                          created_at DATETIME NOT NULL,
                          project_id BIGINT NOT NULL
);

CREATE TABLE file (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      file_name VARCHAR(50) NOT NULL,
                      cloud_file_name VARCHAR(255) NOT NULL,
                      file_content VARCHAR(255),
                      created_at DATETIME NOT NULL,
                      deleted_at DATETIME,
                      user_id BIGINT NOT NULL,
                      folder_id BIGINT NOT NULL,
                      project_id BIGINT NOT NULL
);

