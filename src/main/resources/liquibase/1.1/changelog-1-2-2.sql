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
    id           character varying(36) PRIMARY KEY ,
    user_id      character varying(36),
    table_id     character varying(36),
    note         character varying,
    people_count integer,
    time         timestamp,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (table_id) REFERENCES tables (id)
);
