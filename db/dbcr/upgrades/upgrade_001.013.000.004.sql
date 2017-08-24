declare
       table_exists pls_integer;
  begin

      select count(*) into table_exists
      from "USER_TABLES"
      where TABLE_NAME = 'SUB_BID';

      if table_exists = 1 then
         execute immediate 'drop table "SUB_BID" cascade constraints';
      end if;

  end;

  /
  create table SUB_BID (
     id                varchar2(50) not null,
     text              varchar2(50) not null,
     modified_date     timestamp,
     created_date      timestamp,
     amount            varchar2(50),
     item_fk           varchar2(50),
     version           smallint
  )
  /

  -- foreign keys need to point to a primary key or indexed
  alter table SUB_BID
  add constraint item_id_fk
  foreign key (item_fk)
  references sub_item (id)
  /

  commit
  /