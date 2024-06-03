--liquibase formatted sql

--changeset k2:1
insert into companies (n_id, c_company_name, c_additonal) VALUES (114,'OOO GOGO', 'какая-то комапния');

--changeset k2:2
insert into companies (n_id, c_company_name, c_additonal) VALUES (145,'OOO TODO', 'какая-то комапния');

--changeset k2:3
insert into workers (n_id, n_name, d_birthday, c_phone, c_mail,  n_company_id, n_type)
VALUES (123,'Yargi', '12.03.1994', '+9936287653', 'yari@mail.ru', 114, 'RVP');

--changeset k2:4
insert into workers (n_id, n_name, d_birthday, c_phone, c_mail,  n_company_id, n_type)
VALUES (134,'DXXDLY', '03.10.1974', '+9936253453', 'syd@mail.ru', 145, 'RVP');

--changeset k2:5
insert into users(n_id, c_username, c_login, c_password, c_phone, c_mail)
values (145, 'Менеджер Антон','man_anton','anton', +746839292, 'a.manager@out.com');

--changeset k2:6
insert into users(n_id, c_username, c_login, c_password, c_phone, c_mail)
values (165, 'Босс Боссович','boss','boss', +7476861983, 'b.boss@out.com');

--changeset k2:7
insert into roles (n_id, c_role_name) values (111,'MANAGER');

--changeset k2:8
insert into roles (n_id, c_role_name) values (115,'ADMIN');

--changeset k2:9
insert into users_roles (n_id, n_role_id, n_user_id) values (107,111, 145);

--changeset k2:10
insert into users_roles (n_id, n_role_id, n_user_id) values (187,115, 165);