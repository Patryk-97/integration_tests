--this script initiates db for h2 db (used in test profile)
insert into user (account_status, email, first_name, last_name) values ('CONFIRMED', 'john@domain.com', 'John', 'Steward')
insert into user (account_status, email, first_name) values ('NEW', 'brian@domain.com', 'Brian')
insert into user (id, account_status, email, first_name, last_name) values ('REMOVED', 'chceotobiezapomniec@wp.pl', 'Iza', 'Debowska')
insert into blog_post (entry, user_id) values ('testEntry1', 1)
insert into blog_post (entry, user_id) values ('testEntry2', 2) 
insert into blog_post (entry, user_id) values ('testEntry3', 2)