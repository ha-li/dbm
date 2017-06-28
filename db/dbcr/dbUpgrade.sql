spool off
set termout off
set heading off

spool Upgrade.sql

declare
  rec_count number;
  updaterec_count number;
  sql_str varchar2(100);
begin

  select count(*) into rec_count       from COR_DATABASE_PATCH where DATABASE_PATCH_VERSION = '001.013.000.001' and DATABASE_PATCH_TYPE='SchemaUpgrade';
  select count(*) into updaterec_count from COR_DATABASE_PATCH where DATABASE_PATCH_VERSION = '001.013.000.001' and DATABASE_PATCH_TYPE='DataMigration';

  select decode (rec_count, 0, '@upgrades/upgrade_001.013.000.001.sql',' ') into sql_str from dual;
  dbms_output.put_line(sql_str);

  if (rec_count = 0) then
     insert into COR_DATABASE_PATCH (DATABASE_PATCH_SEQ,DATABASE_PATCH_VERSION,DATABASE_PATCH_TYPE, CREATED_DATE, CREATOR_ID, MODIFIED_DATE, MODIFIED_ID, VERSION)
       values ('10000-000-000-0000', '001.013.000.001', 'SchemaUpgrade', SYSTIMESTAMP, 'System', SYSTIMESTAMP, 'System', 0);
     commit;
  end if;

  select decode (updaterec_count, 0, '@upgrades/upgrade_001.013.000.001_After.sql',' ') into sql_str from dual;
  dbms_output.put_line(sql_str);

  if (updaterec_count = 0) then
     insert into COR_DATABASE_PATCH (DATABASE_PATCH_SEQ,DATABASE_PATCH_VERSION,DATABASE_PATCH_TYPE, CREATED_DATE, CREATOR_ID, MODIFIED_DATE, MODIFIED_ID, VERSION)
       values ('10000-000-000-0001', '001.013.000.001', 'DataMigration', SYSTIMESTAMP, 'System', SYSTIMESTAMP, 'System', 0);
     commit;
  end if;
end;
/

spool off
set termout on
set heading on

spool InstallDB.log append

@Upgrade.sql
