create table cor_database_patch
(
   database_patch_seq varchar2(255),
   created_date timestamp,
   creator_id varchar2(50),
   database_patch_version varchar2(20),
   database_patch_type varchar2(255),
   modified_date timestamp,
   modified_id varchar2(50),
   version number(20)
);

exit;