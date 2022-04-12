-- roles_type
INSERT INTO public.roles_type(name)
VALUES ('ROLE_USER');
INSERT INTO public.roles_type(name)
VALUES ('ROLE_MODERATOR');
INSERT INTO public.roles_type(name)
VALUES ('ROLE_ADMIN');
-- users
INSERT INTO public.users
  (username, email, "password")
VALUES ('test', 'test@gmail.com', 'test');
-- user_roles
INSERT INTO public.user_roles
  (user_id, role_id)
VALUES (1, 1);
