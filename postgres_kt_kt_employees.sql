CREATE SCHEMA IF NOT EXISTS kitchen_talk;

DROP TABLE IF EXISTS kitchen_talk.kt_employees;
DROP SEQUENCE IF EXISTS kitchen_talk.kt_employees_seq;

CREATE SEQUENCE IF NOT EXISTS kitchen_talk.kt_employees_seq;

CREATE TABLE kitchen_talk.kt_employees
(
    id bigint DEFAULT nextval('kitchen_talk.kt_employees_seq'::regclass) NOT NULL,
    first_name varchar(30) NOT NULL,
    second_name varchar(30),
    last_name varchar(40) NOT NULL,
    second_last_name varchar(40),
    email varchar(100) NOT NULL,
    gender varchar(1) DEFAULT 'M'::character varying NOT NULL,
    phone_country smallint,
    phone_region smallint,
    phone_number integer,
    country varchar(20) NOT NULL,
    state varchar(20) NOT NULL,
    city varchar(20) NOT NULL,
    address varchar(60) NOT NULL,
    entry_date timestamp with time zone,
    category varchar(3) DEFAULT 'SA'::character varying NOT NULL,
    level varchar(2) DEFAULT 'N1'::character varying NOT NULL,
    is_active varchar(1) DEFAULT 'Y'::character varying NOT NULL
);

CREATE UNIQUE INDEX key_kt_employees ON kitchen_talk.kt_employees (id);
CREATE UNIQUE INDEX ix_kt_employees_unique_business ON kitchen_talk.kt_employees (first_name, last_name, gender, email);

INSERT INTO kitchen_talk.kt_employees (id, first_name, second_name, last_name, second_last_name, email, gender, phone_country, phone_region, phone_number, country, state, city, address, entry_date, category, level, is_active) VALUES (135718, 'Thedrick', 'Nealy', 'Flippen', 'Priestnall', 'thedrick.flippen.priestnall@everis.com', 'M', 56, 2, 22221641, 'Chile', 'Metropolitana', 'Santiago', '579 Bultman Center', '2001-02-04 07:01:55.000000', 'SN', 'N2', 'Y');
INSERT INTO kitchen_talk.kt_employees (id, first_name, second_name, last_name, second_last_name, email, gender, phone_country, phone_region, phone_number, country, state, city, address, entry_date, category, level, is_active) VALUES (135719, 'Baudoin', 'Yank', 'Josilevich', 'Beagley', 'baudoin.josilevich.beagley@everis.com', 'M', 56, 2, 25432031, 'Chile', 'Metropolitana', 'Santiago', '20 Grim Point', '2011-01-27 00:10:35.000000', 'SA', 'N1', 'Y');
INSERT INTO kitchen_talk.kt_employees (id, first_name, second_name, last_name, second_last_name, email, gender, phone_country, phone_region, phone_number, country, state, city, address, entry_date, category, level, is_active) VALUES (135720, 'Gabbey', 'Jessie', 'Stradling', 'Liepina', 'gabbey.stradling.liepina@everis.com', 'F', 56, 2, 22628876, 'Chile', 'Metropolitana', 'Santiago', '937 Florence Plaza', '2002-02-24 10:18:03.000000', 'STL', 'N2', 'Y');