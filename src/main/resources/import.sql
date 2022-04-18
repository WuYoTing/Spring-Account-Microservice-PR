-- hint: sql must be single line
-- roles_type
Insert into roles_type(name) values ('ROLE_USER');
Insert into roles_type(name) values ('ROLE_MODERATOR');
Insert into roles_type(name) values ('ROLE_ADMIN');

-- User
Insert into users (email, password, refresh_token_id, username) values ('test@gma.co', '123123123123123', null, 'test');

-- user_roles
Insert into user_roles (user_id, role_type_id) values (1, 2);
