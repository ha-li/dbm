--
-- this script will be executed after the schema upgrade
--
-- this script may be hand coded for data migration purposes

set define off;

insert into sub_discount
 (discount_plan_id, discount_frequency, amount, percent)
values
 ('1000-0000-0000-0000', 'MONTHLY', 15.00, null);

insert into sub_discount
 (discount_plan_id, discount_frequency, amount, percent)
values
 ('1000-0000-0000-0001', 'BI-MONTHLY', null, 10.00);

commit;

insert into sub_payment_plan
 (payment_plan_id, pay_interval, amount, discount_plan_fk)
values
 ('1000-0000-0000-0000', 'MONTHLY', 45, '1000-0000-0000-0000');

insert into sub_payment_plan
 (payment_plan_id, pay_interval, amount, discount_plan_fk)
values
 ('1000-0000-0000-0001', 'BI-MONTHLY', 25, '1000-0000-0000-0001');

commit;