create table bases (
  id          serial        not null primary key,
  name        varchar(256)  not null,
  location_id integer       not null,
  foreign key (location_id) references locations(id) on delete cascade
);