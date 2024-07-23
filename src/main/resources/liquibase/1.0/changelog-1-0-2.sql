alter table about_us alter column number type character varying(13);

insert into about_us (id, restaurant_name, address, number, email)
values (1, 'Halal Manqal', 'Nizami Rayounu, Xaqlar Dostlugu', '+99450990xxxx',
        'halal.manqal@gmail.com');