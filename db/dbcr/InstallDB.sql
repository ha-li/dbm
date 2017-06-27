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
  prompt Step 1. Create Tables
  prompt ========================================
  prompt

  @createTables.sql

  prompt
  prompt ========================================
  prompt Step 1. Create Tables
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

