insert into users(dtype,first_name,last_name,email,is_enabled,password,user_status,role) VALUES
    ('Expert','farzad','afshar','f@gmail.com',false,'aA1!aaa','WAITING_FOR_ACCEPT','ROLE_EXPERT');
insert into services(services) VALUES ('Home');
insert into sub_services(sub_services_name,services_id) VALUES ('clean',1);
insert into users(dtype,first_name,last_name,email,is_enabled,password,user_status,role) VALUES
    ('Admin','admin','admin','admin@gmail.com',false,'aA 1!aaa','ACCEPT','ROLE_ADMIN');