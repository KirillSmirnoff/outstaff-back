--liquibase formatted sql

--changeset k2:1

alter table users add constraint unique_login unique (c_login);
alter table users add constraint  unique_email unique (c_mail);