create table reset_password_details
(
    validation_code varchar(255) primary key,
    user_id      varchar(36),
    foreign key (user_id) references users (id),
    created_at   timestamptz not null,
    last_attempt timestamptz,
    confirmed_at timestamptz
)