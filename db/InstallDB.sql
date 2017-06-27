sqlplus subadm/subadm@SUBADM
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
set termout off

spool 'Install.log' append;

prompt
prompt ==============================================
prompt 1. Decide if it is a new install or an upgrade
prompt ==============================================

select
  case when exists (select * from user_objects) then '@@dbcr/install-db.sql'
  else '@@dbcm/create-db.sql'
  end
from dual;

spool off;

exit;
