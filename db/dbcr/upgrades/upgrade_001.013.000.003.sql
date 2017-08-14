declare
       table_exists pls_integer;
  begin

      select count(*) into table_exists
      from "USER_TABLES"
      where TABLE_NAME = 'SUB_MESSAGES';

      if table_exists = 1 then
         execute immediate 'drop table "SUB_MESSAGES" cascade constraints';
      end if;

  end;

  /
  create table SUB_MESSAGES (
     id                varchar2(50) not null,
     text              varchar2(50) not null,
     transient_text    varchar2(100),
     modified_date     timestamp,
     created_date      timestamp,
     type              varchar2(20),
     sender_first      varchar2(50),
     sender_last       varchar2(50)
  )
  /

  alter table sub_messages
  add constraint messages_id_pk
  primary key (id)
  /

  commit
  /

declare
       table_exists pls_integer;
  begin

      select count(*) into table_exists
      from "USER_TABLES"
      where TABLE_NAME = 'SUB_ITEM';

      if table_exists = 1 then
         execute immediate 'drop table "SUB_ITEM" cascade constraints';
      end if;

  end;

  /
  create table SUB_ITEM (
     id                varchar2(50) not null,
     name              varchar2(50) not null,
     auction_end       TIMESTAMP,
     amount            number(19,4),
     currency          varchar2(5),
     zipcode           varchar2(10),
     enc_value         varchar2(1000),
     message_fk        varchar2(50)
  )
  /

  -- foreign keys need to point to a primary key or indexed
  alter table SUB_ITEM
  add constraint message_id_fk
  foreign key (message_fk)
  references sub_messages (id)
  /

  alter table SUB_ITEM
     add constraint item_id_pk
  primary key (id)
  /

  commit
  /