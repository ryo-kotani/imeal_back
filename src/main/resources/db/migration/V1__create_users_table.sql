create table users (
  id        serial        not null primary key,
  name      varchar(256)  not null,
  email     varchar(256)  not null unique,
  password  varchar(256)  not null
);