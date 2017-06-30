  declare
       table_exists pls_integer;
  begin

      select count(*) into table_exists
      from "USER_TABLES"
      where TABLE_NAME = 'SUB_PAYMENT_PLAN';

      if table_exists = 1 then
         execute immediate 'drop table "SUB_PAYMENT_PLAN" cascade constraints';
      end if;

  end;

  /
  create table SUB_PAYMENT_PLAN (
     payment_plan_id varchar2(50) not null,
     pay_interval varchar2(50) not null,
     amount number(10,2) not null,
     discount_plan_fk varchar2(100) not null
  )
  /


declare
       table_exists pls_integer;
  begin

      select count(*) into table_exists
      from "USER_TABLES"
      where TABLE_NAME = 'SUB_DISCOUNT';

      if table_exists = 1 then
         execute immediate 'drop table "SUB_DISCOUNT" cascade constraints';
      end if;

  end;

  /
  create table SUB_DISCOUNT (
     discount_plan_id varchar2(50) not null,
     discount_frequency varchar2(50) not null,
     amount number(10,2),
     percent number(10,2)
  )
  /
  commit
  /