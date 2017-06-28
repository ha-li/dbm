  declare
       table_exists pls_integer;
  begin

      select count(*) into table_exists
      from "USER_TABLES"
      where TABLE_NAME = 'SUB_PUBLICATION';

      if table_exists = 1 then
         execute immediate 'drop table "SUB_PUBLICATION" cascade constraints';
      end if;

  end;

  /
  create table SUB_PUBLICATION (
     publication_id varchar2(50) not null,
     pub_interval varchar2(50) not null,
     pub_name varchar2(255) not null,
     publisher_fk varchar2(100) not null,
     pub_plan_fk varchar2(100)
  )
  /

  commit
  /