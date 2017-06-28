  ----------------------------------
  -- a db install script --
  ----------------------------------

  whenever oserror exit 1
  whenever sqlerror exit sql.sqlcode
  set define on
  set echo off
  set feedback off
  set trimspool on
  set sqlcase mixed
  set arraysize 1
  set verify off
  set serveroutput on
  spool InstallDB.log append

  select to_char(sysdate, 'MM/DD/YYYY HH24:MM:SS') as "Start Time" from dual
  /

  select user as "USER Name" from dual
  /

  prompt
  prompt ========================================
  prompt Step 1. Determine whether Create or Update Tables
  prompt ========================================
  prompt

  spool off

  set termout off
  set heading off
  spool createOrUpdateDB.sql

  select
     case when exists (select * from user_objects) then '@dbUpgrade.sql'
     else '@createTables.sql'
     end
  from dual;

  spool off
  set termout on
  set heading on

  spool InstallDB.log append

  prompt
  prompt ========================================
  prompt Step 2. Create/Update database
  prompt ========================================
  prompt

  @createOrUpdateDB.sql

  prompt
  prompt ========================================
  prompt Step 2. System Parameters
  prompt ========================================
  prompt

  @systemparameter.sql

  prompt
  prompt ========================================
  prompt Step 3. Post DB Install
  prompt ========================================
  prompt

  set define off

  set define on

  select to_char(sysdate, 'MM/DD/YYYY HH24:MM:SS') as "End Time" from dual
  /

  spool off
  /

  exit
  /

