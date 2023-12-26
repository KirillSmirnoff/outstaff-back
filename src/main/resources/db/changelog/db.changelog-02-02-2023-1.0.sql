--liquibase formatted sql

--changeset k2:1
alter table roles add constraint uniq_role_name unique(c_role_name) ;
--changeset k2:2
alter table users add column n_delete integer not null default 0;
