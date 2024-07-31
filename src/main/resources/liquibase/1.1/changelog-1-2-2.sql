CREATE TABLE tables (
    id character varying(36) PRIMARY KEY ,
    no integer unique ,
    floor integer,
    capacity integer,
    cigarette boolean,
    window_view boolean
)