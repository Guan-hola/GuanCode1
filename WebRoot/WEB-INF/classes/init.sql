alter table user
	add column role varchar(10) NULL after email;
	
update user set role='admin' where id<=10;
update user set role='user' where id>10;
