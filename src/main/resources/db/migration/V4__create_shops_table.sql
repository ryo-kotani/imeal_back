create table shops (
  id          serial          not null primary key,
  url         varchar(256),
  name        varchar(256)    not null,
  address     varchar(256)    not null,
  distance    integer         not null,
  minutes     integer         not null,
  base_id     integer         not null,
  location_id integer         not null,
  foreign key (base_id) references bases(id) on delete cascade,
  foreign key (location_id) references locations(id) on delete cascade
);