create table users (
  id        serial        not null primary key,
  name      varchar(10)  not null,
  email     varchar(256)  not null unique,
  -- BCryptPasswordEncoderの入力上限: 72Byte, 出力: 約60文字より設定
  password  varchar(72)  not null
);