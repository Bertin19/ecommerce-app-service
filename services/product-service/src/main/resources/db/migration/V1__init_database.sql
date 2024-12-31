create table if not exists category
(
    id varchar(255) PRIMARY KEY NOT NULL ,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);

create table if not exists product
(
    id varchar(255) PRIMARY KEY NOT NULL ,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    available_quantity DOUBLE PRECISION NOT NULL,
    price NUMERIC(38, 2),
    category_id VARCHAR
        CONSTRAINT fkqfot8nih9aqqj3ql4ig0ei9ea
            references category
);

CREATE SEQUENCE IF NOT EXISTS category_seq INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS product_seq INCREMENT BY 50;

