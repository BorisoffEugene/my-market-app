-- схема
create schema if not exists market;

-- таблица items
create table if not exists market.items(
	id bigserial primary key,
    title varchar not null,
    description varchar,
    img_path varchar,
    price bigint not null check(price > 0),
    count int not null default 0 check(count >= 0)
);

comment on table market.items is 'Карточка товара';
comment on column market.items.id is 'ID записи';
comment on column market.items.title is 'Название';
comment on column market.items.description is 'Описание';
comment on column market.items.img_path is 'Путь к изображению товара';
comment on column market.items.price is 'Цена';
comment on column market.items.count is 'Количество товаров в корзине';

create index if not exists idx_items_title on market.items (title);
create index if not exists idx_items_description on market.items (description);

-- таблица orders
create table if not exists market.orders(
	id bigserial primary key,
	total_sum bigint not null check(total_sum > 0)
);

comment on table market.orders is 'Заказы';
comment on column market.orders.id is 'ID записи';
comment on column market.orders.total_sum is 'Суммарная стоимость заказа';

-- таблица order_items
create table if not exists market.order_items(
	id bigserial primary key,
	order_id bigint not null references market.orders(id) on delete cascade,
	title varchar not null,
	count int not null check(count > 0),
	price bigint not null check(price > 0)
);

comment on table market.order_items is 'Состав заказа';
comment on column market.order_items.id is 'ID записи';
comment on column market.order_items.order_id is 'ID заказа (orders.id)';
comment on column market.order_items.title is 'Название товара в заказе';
comment on column market.order_items.count is 'Количество товаров в заказе';
comment on column market.order_items.price is 'Цена товара на момент покупки';

create index if not exists idx_order_items_order_id on market.order_items (order_id);

-- таблица cart
create table if not exists market.cart(
	id bigserial primary key,
	status varchar not null default 'CURRENT',
	total bigint not null default 0 check(total >= 0)
);

comment on table market.cart is 'Заказы';
comment on column market.cart.id is 'ID записи';
comment on column market.cart.status is 'Статус корзины (CURRENT, DELETED, SOLD)';
comment on column market.cart.total is 'Суммарная цена товаров в корзине';

create index if not exists idx_cart_status on market.cart (status);

-- таблица cart_items
create table if not exists market.cart_items(
	id bigserial primary key,
	cart_id bigint not null references market.cart(id) on delete cascade,
	item_id bigint not null unique references market.items(id) on delete cascade,
	count int not null default 1 check(count >= 0)
);

comment on table market.cart_items is 'Список товаров в корзине';
comment on column market.cart_items.id is 'ID записи';
comment on column market.cart_items.cart_id is 'ID корзины (cart.id)';
comment on column market.cart_items.item_id is 'ID товара (items.id)';
comment on column market.cart_items.count is 'Количество товаров в корзине';

create index if not exists idx_cart_items_cart_id on market.cart_items (cart_id);
create index if not exists idx_cart_items_item_id on market.cart_items (item_id);

-- таблица
create table if not exists market.users(
	login text primary key,
	pass text not null
);

comment on table market.users is 'Список пользователей магазина';
comment on column market.users.login is 'Логин';
comment on column market.users.pass is 'Пароль';