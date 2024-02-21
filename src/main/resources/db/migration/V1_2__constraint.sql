ALTER TABLE project ADD CONSTRAINT fk_project_user_id FOREIGN KEY (user_id) REFERENCES member(id);

ALTER TABLE folder ADD CONSTRAINT fk_folder_parent_id FOREIGN KEY (parent_id) REFERENCES folder(id),
                   ADD CONSTRAINT fk_folder_user_id FOREIGN KEY (user_id) REFERENCES member(id),
                   ADD CONSTRAINT fk_folder_project_id FOREIGN KEY (project_id) REFERENCES project(id);

ALTER TABLE file ADD CONSTRAINT fk_file_user_id FOREIGN KEY (user_id) REFERENCES member(id),
                 ADD CONSTRAINT fk_file_folder_id FOREIGN KEY (folder_id) REFERENCES folder(id),
                 ADD CONSTRAINT fk_file_project_id FOREIGN KEY (project_id) REFERENCES project(id);

ALTER TABLE chat_message ADD CONSTRAINT fk_chat_message_chatroom_id FOREIGN KEY (chatroom_id) REFERENCES chatroom(id),
                         ADD CONSTRAINT fk_chat_message_user_id FOREIGN KEY (user_id) REFERENCES member(id);

ALTER TABLE chatroom ADD CONSTRAINT fk_chatroom_project_id FOREIGN KEY (project_id) REFERENCES project(id);

ALTER TABLE project_member ADD CONSTRAINT fk_project_member_user_id FOREIGN KEY (user_id) REFERENCES member(id),
                           ADD CONSTRAINT fk_project_member_project_id FOREIGN KEY (project_id) REFERENCES project(id);
