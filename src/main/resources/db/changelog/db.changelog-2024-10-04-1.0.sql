--liquibase formatted sql

--changeset k2:1
alter table roles drop column n_delete ;