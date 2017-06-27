declare
       table_exists pls_integer;
  begin

      select count(*) into table_exists
      from "USER_TABLES"
      where TABLE_NAME = 'MONKEYBUTT';

      if table_exists = 1 then
         execute immediate 'drop table "MONKEYBUTT" cascade constraints';
      end if;

  end;

  /
  create table monkeybutt (
     customer_id varchar2(50) not null,
     name varchar2(255) not null,
     subscription varchar2(100) not null
  )
  /

  commit
  /