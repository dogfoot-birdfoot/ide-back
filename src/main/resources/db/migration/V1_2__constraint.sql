alter table project add constraint fk_project__owner foreign key(owner) references account(id);
alter table project add constraint fk_project__author foreign key(author) references account(id);

alter table folder add constraint fk_folder__projectid foreign key (project_id) references project(id);
alter table folder add constraint fk_folder__parentid foreign key (parent_id) references folder(id);
alter table folder add constraint fk_folder__author foreign key (author) references account(id);

alter table file add constraint fk_file__projectid foreign key (project_id) references project(id);
alter table file add constraint fk_file__folderid foreign key (folder_id) references folder(id);
alter table file add constraint fk_file__author foreign key (author) references account(id);
