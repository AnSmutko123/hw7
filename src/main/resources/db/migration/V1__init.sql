create table if not exists products
(
    id    bigserial    primary    key,
    title    varchar(255),
    cost int
);

insert into products (title, cost)
values ('Milk', 70),
       ('Water', 30),
       ('Bread', 40),
       ('Apples', 100),
       ('Oranges', 120),
       ('Lamb', 300),
       ('Chicken', 200),
       ('Banana', 150),
       ('Tomato', 160),
       ('Chocolate', 50),
       ('Sugar', 50),
       ('Salt', 30),
       ('Cookies', 90),
       ('Cucumber', 100),
       ('Coffee', 95),
       ('Tea', 50),
       ('Butter', 200),
       ('Potato', 50),
       ('Oil', 90),
       ('Onion', 40);

drop table users if exists cascade;
create table users (
                       id         bigserial primary key,
                       username   varchar(36) not null,
                       password   varchar(80) not null,
                       name      varchar(50),
                       email      varchar(50) unique,
                       created_at timestamp default current_timestamp,
                       updated_at timestamp default current_timestamp
);

drop table authorities if exists cascade;
create table authorities
(
    id   bigserial primary key,
    name varchar(255) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

drop table roles if exists cascade;
create table roles
(
    id   serial,
    name varchar(50) not null,
    primary key (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

drop table users_roles if exists cascade;
create table users_roles
(
    user_id bigint not null,
    role_id int    not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);

drop table roles_authorities if exists cascade;
create table roles_authorities
(
    role_id      int not null,
    authority_id int not null,
    primary key (role_id, authority_id),
    foreign key (role_id) references roles (id),
    foreign key (authority_id) references authorities (id)
);

drop table users_authorities if exists cascade;
create table users_authorities
(
    user_id      int not null,
    authority_id int not null,
    primary key (user_id, authority_id),
    foreign key (user_id) references users (id),
    foreign key (authority_id) references authorities (id)
);

insert into users (username, password, name, email)
values ('user1', '$2a$12$CaLIJZlpbxQmxXiAaDZ0keGlcjzlHhrbAzgwqgMTQg89XGfjXedYS', 'Alice', 'alice@gmail.com'),
       ('user2', '$2a$12$CaLIJZlpbxQmxXiAaDZ0keGlcjzlHhrbAzgwqgMTQg89XGfjXedYS', 'Jill', 'jill@gmail.com'),
       ('user3', '$2a$12$CaLIJZlpbxQmxXiAaDZ0keGlcjzlHhrbAzgwqgMTQg89XGfjXedYS', 'John', 'jhon@gmail.com'),
       ('user4', '$2a$12$CaLIJZlpbxQmxXiAaDZ0keGlcjzlHhrbAzgwqgMTQg89XGfjXedYS', 'Archi', 'archi@gmail.com');

insert into authorities (name)
values ('READ_ADMIN_PAGE'),
       ('READ_USER_INFO'),
       ('READ_UNIQ_PAGE'),
       ('READ_DEMO_PAGE'),
       ('READ_DEMO2_PAGE');

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 1),
       (2, 2);

insert into roles_authorities (role_id, authority_id)
values (1, 2),
       (1, 4),
       (2, 1),
       (2, 2);

insert into users_authorities (user_id, authority_id)
values (3, 3),
       (1, 5);
