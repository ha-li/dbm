declare
       table_exists pls_integer;
  begin

      select count(*) into table_exists
      from "USER_TABLES"
      where TABLE_NAME = 'SUB_DESCRIPTION';

      if table_exists = 1 then
         execute immediate 'drop table "SUB_DESCRIPTION" cascade constraints';
      end if;

  end;

  /
  create table SUB_DESCRIPTION (
     id                varchar2(50) not null,
     modified_date     timestamp,
     created_date      timestamp,
     description       varchar2(50)
  )
  /

  -- foreign keys need to point to a primary key or indexed
  alter table SUB_DESCRIPTION
  add constraint desc_id_pk
  primary key (id)
  /

  alter table SUB_ITEM
  add constraint desc_id_fk
  foreign key (description_fk)

  commit
  /