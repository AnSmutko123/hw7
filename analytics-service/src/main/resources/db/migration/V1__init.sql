drop table bought_products if exists cascade;
create table products_analytics
(
    id                 bigserial primary key,
    product_id         bigint       not null,
    product_title      varchar(255) not null,
    bought_date        date,
    added_to_cart_date date

);

insert into products_analytics (product_id, product_title, bought_date, added_to_cart_date)
values ('1', 'Milk', '0001-01-01', '0001-01-01'),
       ('1', 'Milk', '0001-01-01', '0001-01-01'),
       ('2', 'Bread', '2022-01-22', '2022-01-22'),
       ('3', 'Apples', '2022-02-04', '2022-02-04'),
       ('1', 'Milk', '0001-01-01', '0001-01-01');

