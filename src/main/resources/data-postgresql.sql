-- roles_type
INSERT INTO roles_type(name)
VALUES ('ROLE_USER');
INSERT INTO roles_type(name)
VALUES ('ROLE_MODERATOR');
INSERT INTO roles_type(name)
VALUES ('ROLE_ADMIN');

-- User
INSERT INTO users (email, password, refresh_token_id, username)
values ('test@gma.co', '123123123123123', null, 'test')

-- user_roles
INSERT INTO user_roles (user_id, role_type_id)
values (1, 2)
