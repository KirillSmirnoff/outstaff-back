--liquibase formatted sql

--changeset k2:1
alter table users_roles
    drop constraint users_roles_n_role_id_fkey,
    add constraint users_roles_n_role_id_fkey foreign key (n_role_id) references roles (n_id)
        on delete cascade;

alter table users_roles
    drop constraint users_roles_n_user_id_fkey,
    add constraint users_roles_n_user_id_fkey foreign key (n_user_id) references users (n_id)
        on delete cascade;