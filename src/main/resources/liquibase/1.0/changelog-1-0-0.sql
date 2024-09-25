create table banners
(
    id    character varying(36) primary key,
    name  character varying unique,
    text  character varying,
    image character varying
);

create table about_us
(
    id             int primary key,
    restaurant_name character varying,
    address        character varying,
    number         character varying(8),
    email          character varying
);

alter table about_us alter column number type character varying(13);

insert into about_us (id, restaurant_name, address, number, email)
values (1, 'Halal Manqal', 'Nizami Rayounu, Xaqlar Dostlugu', '+99450990xxxx',
        'halal.manqal@gmail.com');