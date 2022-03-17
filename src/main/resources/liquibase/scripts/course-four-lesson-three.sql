--liquibase formatted sql

--changeSet apakhomov:1
CREATE INDEX student_name_index ON student(name);
--changeSet apakhomov:2
CREATE INDEX faculty_name_color_index ON faculty(color, name)
