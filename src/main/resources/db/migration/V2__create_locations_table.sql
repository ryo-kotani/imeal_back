create table locations (
  id  serial        not null primary key,
  lat numeric(8, 6) not null,
  lon numeric(9, 6) not null
);