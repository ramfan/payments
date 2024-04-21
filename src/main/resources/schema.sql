create table if not exists loan_payment_type
(
    id   bigserial primary key,
    type varchar not null unique
);

create table if not exists credit_type
(
    id   bigserial primary key,
    type varchar not null unique
);

create table if not exists person
(
    id        bigserial primary key,
    full_name varchar not null,
    username  varchar not null unique,
    password  varchar not null,
    enabled   bool    not null default false
);

create table if not exists role
(
    id   bigserial primary key,
    name varchar not null unique
);

create table if not exists operation
(
    id   bigserial primary key,
    name varchar not null unique
);

create table if not exists person_role
(
    id bigserial primary key,
    person_id bigserial,
    role_id bigserial,
    foreign key (person_id) references person(id),
    foreign key (role_id) references role(id)
);

create table if not exists role_operation
(
    id bigserial primary key,
    operation_id bigserial,
    role_id bigserial,
    foreign key (operation_id) references operation(id),
    foreign key (role_id) references role(id)
);

create table if not exists credit
(
    id             bigserial primary key,
    name           varchar,
    credit_size    bigint not null
        constraint valid_credit_size check ( credit_size > 0 ),
    percent        float  not null check ( percent >= 0 AND percent <= 100 ),
    start_date     date   not null,
    months_count   bigint not null check ( months_count > 0 ),
    loan_balance   bigint not null
        constraint valid_loan_balance check ( loan_balance > 0 ),
    borrower_id    bigserial,
    credit_type_id bigserial,
    foreign key (borrower_id) references person (id),
    foreign key (credit_type_id) references credit_type (id)
);

create table if not exists loan_payment
(
    id              bigserial primary key,
    value           bigint not null check ( value > 0 ),
    loan_id         bigserial,
    payment_type_id bigserial,
    foreign key (loan_id) references credit (id),
    foreign key (payment_type_id) references loan_payment_type (id)
);

