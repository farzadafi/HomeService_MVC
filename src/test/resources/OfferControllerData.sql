insert into users(dtype,first_name,last_name,email,is_enabled,password,user_status,role,stars) VALUES
    ('Expert','farzadExpert','afshar','fe@gmail.com',true,'aA1!aaa','ACCEPT','ROLE_EXPERT',0);

insert into users(dtype,first_name,last_name,email,is_enabled,password,user_status,role) VALUES
    ('Customer','farzadCustomer','afshar','fc@gmail.com',true,'aA1!aaa','ACCEPT','ROLE_CUSTOMER');

insert into services(services) VALUES
    ('Home');

insert into sub_services(sub_services_name,services_id) VALUES
    ('clean',1);

insert into orders(customer_id,sub_service_id,proposed_price,order_status,city,description) VALUES
    (2,1,30000,'EXPERT_SUGGESTION','Kerman','please fix it!');