CREATE TABLE public.users
(
  id         bigserial    NOT NULL,
  email      varchar(255) NULL,
  "password" varchar(255) NULL,
  username   varchar(255) NULL,
  CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
  CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username),
  CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE public.user_roles
(
  user_id      int8 NOT NULL,
  role_type_id int4 NOT NULL,
  CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_type_id),
  CONSTRAINT fkfdhtmw4ohw7ra8mlu78axpuye FOREIGN KEY (role_type_id) REFERENCES public.roles_type (id),
  CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES public.users (id)
);


CREATE TABLE public.roles_type
(
  id     serial4     NOT NULL,
  "name" varchar(20) NULL,
  CONSTRAINT roles_type_pkey PRIMARY KEY (id)
);

CREATE TABLE public.refresh_token
(
  id          int8         NOT NULL,
  expiry_date timestamp    NOT NULL,
  "token"     varchar(255) NOT NULL,
  user_id     int8         NULL,
  CONSTRAINT refresh_token_pkey PRIMARY KEY (id),
  CONSTRAINT uk_r4k4edos30bx9neoq81mdvwph UNIQUE (token),
  CONSTRAINT fkjtx87i0jvq2svedphegvdwcuy FOREIGN KEY (user_id) REFERENCES public.users (id)
);

