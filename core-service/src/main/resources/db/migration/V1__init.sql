drop table categories if exists cascade;
create table categories
(
    id         bigserial primary key,
    title      varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

drop table products if exists cascade;
create table products
(
    id          bigserial primary key,
    title       varchar(255),
    price       numeric (8,2),
    category_id bigint not null references categories (id),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

insert into categories (title)
values ('Молочные продукты'),
       ('Хлебобулочные изделия'),
       ('Овощи');

insert into products (title, price, category_id)
values ('Milk', 70.00, 1),
       ('Water', 30.00, 1),
       ('Bread', 40.00, 1),
       ('Apples', 100.00, 1),
       ('Oranges', 120.00, 1),
       ('Lamb', 300.00, 2),
       ('Chicken', 200.00, 3),
       ('Banana', 150.00, 1),
       ('Tomato', 160.00, 3),
       ('Chocolate', 50.00, 2),
       ('Sugar', 50.00, 1),
       ('Salt', 30.00, 3),
       ('Cookies', 90.00, 1),
       ('Cucumber', 100.00, 1),
       ('Coffee', 95.00, 1),
       ('Tea', 50.00, 2),
       ('Butter', 200.00, 2),
       ('Potato', 50.00, 1),
       ('Oil', 90.00, 1),
       ('Onion', 40.00, 3);


drop table orders if exists cascade;
create table orders
(
    id          bigserial primary key,
    username    varchar(255) not null,
    total_price numeric (8,2)      not null,
    address     varchar(255),
    phone       varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

drop table order_items if exists cascade;
create table order_items
(
    id                bigserial primary key,
    product_id        bigint references products (id),
    category_id       bigint references categories (id),
    order_id          bigint references orders (id),
    quantity          integer,
    price_per_product numeric (8,2),
    price             numeric (8,2),
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);

insert into orders (username, total_price, address, phone)
values ('user1', 200.00, 'address', '46545');
