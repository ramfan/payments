create table if not exists person (
    id serial primary key,
    full_name varchar not null
);

create table if not exists credit (
    id serial primary key,
    name varchar,
    credit_size bigint not null,
    percent float not null,
    start_date date not null,
    months_count bigint not null,
    borrower_id bigserial,
    foreign key (borrower_id) references person(id)
)