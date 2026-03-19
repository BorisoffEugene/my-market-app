-- схема
create schema if not exists market;

-- таблица items
create table if not exists market.items(
	id bigint primary key,
    title varchar,
    description varchar,
    imgPath varchar,
    price bigint
);

comment on table market.items is 'Карточка товара';
comment on column market.items.id is 'ID записи';
comment on column market.items.title is 'Название';
comment on column market.items.description is 'Описание';
comment on column market.items.imgPath is 'Путь к изображению товара';
comment on column market.items.price is 'Цена';

create index if not exists idx_items_title on market.items (title);
create index if not exists idx_items_description on market.items (description);