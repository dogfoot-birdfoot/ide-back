CREATE DATABASE ide char set utf8mb4 collate utf8mb4_bin;

create user 'ide'@'localhost' identified by 'ide321';
create user 'ide'@'%' identified by 'ide321';
grant all on ide.* to 'ide'@'localhost';
grant all on ide.* to 'ide';