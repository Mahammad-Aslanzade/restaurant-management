CREATE TABLE users
(
    id           character varying(36) PRIMARY KEY,
    name         character varying        NOT NULL,
    surname      character varying,
    birth_date   date,
    email        character varying UNIQUE NOT NULL,
    phone_number character varying(13)
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
