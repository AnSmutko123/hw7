create table if not exists products (id bigserial primary key, title varchar(255), cost int);

insert into products (title, cost)
values
('Milk', 70),
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