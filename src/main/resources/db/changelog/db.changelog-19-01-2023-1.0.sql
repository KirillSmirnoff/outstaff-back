--liquibase formatted sql

--changeset k2:1
alter table roles alter column n_delete set data type integer;
--changeset k2:2
alter table users_roles alter column n_role_id set data type bigint;
--changeset k2:3
alter table users_roles alter column n_user_id set data type bigint;