drop table categories if exists cascade;
create table categories
(
    id              bigserial    primary    key,
    title           varchar(255),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

drop table products if exists cascade;
create table products
(
    id                  bigserial    primary    key,
    title               varchar(255),
    price               int,
    category_id         bigint not null references categories(id),
    created_at          timestamp default current_timestamp,
    updated_at          timestamp default current_timestamp
);

insert into categories (title)
values ('Молочные продукты'),
       ('Хлебобулочные изделия'),
       ('Овощи');

insert into products (title, price, category_id)
values ('Milk', 70, 1),
       ('Water', 30, 1),
       ('Bread', 40, 1),
       ('Apples', 100, 1),
       ('Oranges', 120, 1),
       ('Lamb', 300, 2),
       ('Chicken', 200, 3),
       ('Banana', 150, 1),
       ('Tomato', 160, 3),
       ('Chocolate', 50, 2),
       ('Sugar', 50, 1),
       ('Salt', 30, 3),
       ('Cookies', 90, 1),
       ('Cucumber', 100, 1),
       ('Coffee', 95, 1),
       ('Tea', 50, 2),
       ('Butter', 200, 2),
       ('Potato', 50, 1),
       ('Oil', 90, 1),
       ('Onion', 40, 3);


drop table orders if exists cascade;
create table orders (
    id              bigserial primary key,
    username        varchar(255) not null ,
    total_price     integer not null,
    address         varchar(255),
    phone           varchar(255),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

drop table order_items if exists cascade;
create table order_items (
    id                      bigserial primary key,
    product_id              bigint references products(id),
    category_id             bigint references categories(id),
    order_id                bigint references orders(id),
    quantity                integer,
    price_per_product       integer,
    price                   integer,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp
);

insert into orders (username, total_price, address, phone)
values ('user1', 200, 'address', '46545');
