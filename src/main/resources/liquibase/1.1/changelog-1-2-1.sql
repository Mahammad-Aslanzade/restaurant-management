CREATE TABLE users
(
    id           character varying(36) PRIMARY KEY,
    name         character varying        NOT NULL,
    surname      character varying,
    username     character varying        NOT NULL,
    password     character varying        NOT NULL,
    birth_date   date,
    email        character varying UNIQUE NOT NULL,
    phone_number character varying(13),
    created_at   date,
    role         character varying        NOT NULL check ( role in ('USER', 'MODERATOR', 'ADMIN'))
);

CREATE TABLE email_verifications
(
    id                  character varying(36) NOT NULL,
    email               character varying     NOT NULL,
    verification_code   character varying     NOT NULL,
    issue_date          TIMESTAMP             NOT NULL,
    verification_status character varying     NOT NULL check ( verification_status in ('PENDING', 'FAILED', 'VERIFICATED')),
    PRIMARY KEY (id)
);

CREATE TABLE addresses
(
    id           character varying(36) PRIMARY KEY,
    name         character varying,
    city         character varying NOT NULL,
    street       character varying NOT NULL,
    apartment    character varying,
    address_type character varying NOT NULL CHECK (address_type IN ('HOME', 'OFFICE')),
    user_id      character varying,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE feedbacks
(
    id      character varying(36) PRIMARY KEY,
    text    character varying,
    rate    float,
    meal_id character varying,
    FOREIGN KEY (meal_id) REFERENCES meals (id),
    check ( rate >= 0 AND rate <= 5),
    user_id character varying(36),
    FOREIGN KEY (user_id) REFERENCES users (id)
)
