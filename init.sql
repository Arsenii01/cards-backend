create table if not exists public.users
(
    user_id bigserial primary key,
    email    varchar(255) not null,
    created_date timestamp not null,
    modified_date timestamp not null,
);

create table if not exists public.email_verification_code
(
    email varchar primary key,
    code varchar not null,
    created_date timestamp with timezone not null,
    modified_date timestamp with timezone not null
);

create table if not exists public.card
(
    card_id bigserial primary key,
    user_id bigint references users(user_id),
    card_name varchar(60) not null,
    full_name varchar(100) not null,
    surname varchar(60),
    position varchar(60),
    phone_number varchar(30),
    email varchar(60),
    degree varchar(120),
    about_me text,
    address varchar(250),
    created_date TIMESTAMPTZ not null,
    modified_date TIMESTAMPTZ not null
);

create table if not exists public.card_link (
    landing_id char(8) primary key,
    card_id bigint references card(card_id) unique,
    created_date TIMESTAMPTZ not null
);

create table if not exists public.social_network (
    record_id bigserial primary key,
    card_id bigint references card(card_id),
    social_network_name varchar(30) not null,
    link varchar(60) not null,
    created_date timestamptz not null,
    modified_date timestamptz not null
)

create table if not exists public.company_info(
    card_id bigint primary key references card(card_id),
    company_name varchar(70) not null,
    business_line varchar(40),
    address varchar(250),
    phone_number varchar(30),
    email varchar(50),
    company_website varchar(60),
    created_date timestamptz not null,
    modified_date timestamptz not null
);