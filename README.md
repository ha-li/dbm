# dbm
database stuff

Bitronix and Oracle Datasources 

Bitronix attempts to recover all resources during startup. 
Failure on any will throw an exception.

To solve this, the user needs the following privileges:
  grant select on sys.dba_pending_transactions to yourschema;
  grant select on sys.pending_trans$ to yourschema;
  grant select on sys.dba_2pc_pending to yourschema;
  grant execute on sys.dbms_system to yourschema;
  
  