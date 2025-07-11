create table reviews (
  id          serial        not null primary key,
  img_path    varchar(256)  not null,
  comment     varchar(256)  not null,
  amount      integer       not null,
  evaluation  integer       not null,
  created_at  timestamp     not null default current_timestamp,
  shop_id     integer       not null,
  foreign key (shop_id) references shops(id) on delete cascade
);