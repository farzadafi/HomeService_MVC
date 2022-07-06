delete from users where id = 1;
insert into users(dtype,first_name,last_name,email,is_enabled,password,user_status,role) VALUES
    ('Expert','farzad','afshar','f@gmail.com',false,'aA1!aaa','WAITING_FOR_ACCEPT','ROLE_EXPERT');