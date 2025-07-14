create table users (
  id        serial        not null primary key,
  name      varchar(10)  not null,
  email     varchar(256)  not null unique,
  -- エンコードされた値が60字ぐらいなので
  password  varchar(72)  not null
);