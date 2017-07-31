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
     id varchar2(50) not null,
     text varchar2(50) not null
  )
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
     id varchar2(50) not null,
     name varchar2(50) not null,
     auctionEnd  TIMESTAMP
  )
  /

  commit
  /