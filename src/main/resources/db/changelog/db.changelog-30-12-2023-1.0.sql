--liquibase formatted sql

--changeset k2:1
alter table companies alter column n_id set data type bigint;

--changeset k2:2
alter table workers alter column n_company_id set data type bigint;
