CREATE TABLE meals
(
    id            character varying(36) PRIMARY KEY,
    title         character varying NOT NULL,
    ingredients   character varying,
    category      character varying(36),
    foreign key (category) references meal_categories (id),
    image         character varying,
    description   character varying,
    carbohydrates float,
    calories      float,
    fat           float,
    gram          float,
    protein       float,
    price         float,
    sale_price     float,
    rate          float
)
;