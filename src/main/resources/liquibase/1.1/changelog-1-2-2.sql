CREATE TABLE tables
(
    id          character varying(36) PRIMARY KEY,
    no          integer unique,
    floor       integer,
    capacity    integer,
    cigarette   boolean,
    window_view boolean
);

CREATE TABLE reservations
(
    id           character varying(36) PRIMARY KEY,
    user_id      character varying(36),
    table_id     character varying(36),
    note         character varying,
    people_count integer,
    time         timestamp,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (table_id) REFERENCES tables (id)
);

CREATE TABLE orders
(
    id           character varying(36) PRIMARY KEY,
    user_id      character varying(36),
    total_price  float,
    meals        character varying DEFAULT '[]',
    address_id   character varying(36),
    status       character varying NOT NULL CHECK ( status in ('PREPARING', 'READY' , 'DELIVERING', 'TAKEN', 'PLANNED')),
    payment_type character varying NOT NULL CHECK ( payment_type in ('CASH', 'CARD') ),
    type         character varying NOT NULL CHECK ( type in ('TAKEAWAY', 'DELIVERY', 'PLANNED')),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (address_id) REFERENCES addresses (id)
);
