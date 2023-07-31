create database tutorial_spring;
use tutorial_spring;

create user 'default'@'%' identified by 'defaultPassword';
create user 'dev'@'%' identified by 'devPassword';
create user 'test'@'%' identified by 'testPassword';

grant all on tutorial_spring.* to 'default'@'%';
grant all on tutorial_spring.* to 'dev'@'%';
grant all on tutorial_spring.* to 'test'@'%';