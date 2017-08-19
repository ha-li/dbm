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
     id varchar2(50) not null,
     pub_interval varchar2(50) not null,
     pub_name varchar2(255) not null,
     publisher_fk varchar2(100) not null,
     pub_plan_fk varchar2(100)
  )
  /

  declare
       table_exists pls_integer;
  begin

      select count(*) into table_exists
      from "USER_TABLES"
      where TABLE_NAME = 'COR_SYSTEM_PARAMETER';

      if table_exists = 1 then
         execute immediate 'drop table "COR_SYSTEM_PARAMETER" cascade constraints';
      end if;

  end;

  /
  create table SYSTEM_PARAMETER (
     system_parameter_seq varchar2(255) not null,
     category varchar2(100),
     code varchar2(255) not null,
     value varchar2(1000) not null,
     created_date timestamp not null,
     creator_id varchar2(50) not null,
     is_encrypted number(1) not null,
     can_cache number(1) default 0,
     version number(10) not null,
     volatile number(1) default 0,
     realm_id number (19) default -1
  )
  /


  commit
  /