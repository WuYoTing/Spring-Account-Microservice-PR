-- roles_type
INSERT INTO public.roles_type(name)
VALUES ('ROLE_USER');
INSERT INTO public.roles_type(name)
VALUES ('ROLE_MODERATOR');
INSERT INTO public.roles_type(name)
VALUES ('ROLE_ADMIN');
-- users
INSERT INTO public.users
  (username, email, "password", refresh_token_id)
VALUES ('test', 'test@gmail.com', 'test', NULL);
