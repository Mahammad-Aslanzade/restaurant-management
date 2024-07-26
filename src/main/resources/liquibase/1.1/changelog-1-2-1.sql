CREATE TABLE users
(
    id          character varying(36) PRIMARY KEY,
    name        character varying        NOT NULL,
    surname     character varying,
    email       character varying UNIQUE NOT NULL,
    phone_number character varying(13)
);

CREATE TABLE addresses
(
    id          character varying(36) PRIMARY KEY,
    name        character varying,
    city        character varying NOT NULL,
    street      character varying NOT NULL,
    apartment   character varying,
    address_type character varying NOT NULL CHECK (address_type IN ('HOME', 'OFFICE')),
    user_id     character varying,
    FOREIGN KEY (user_id) REFERENCES users (id)
);
