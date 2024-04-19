--liquibase formatted sql

--changeset k2:1

alter table workers drop column b_status;