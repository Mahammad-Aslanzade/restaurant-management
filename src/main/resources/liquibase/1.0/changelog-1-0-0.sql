create table banners
(
    id    character varying(36) primary key,
    name  character varying unique,
    text  character varying,
    image character varying
)