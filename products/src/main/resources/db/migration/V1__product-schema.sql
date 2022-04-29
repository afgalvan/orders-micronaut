CREATE TABLE PRODUCTS
(
    id                 SERIAL                      PRIMARY KEY,
    name               VARCHAR(255)                NOT NULL,
    image_uri          VARCHAR(255)                NOT NULL,
    price              NUMERIC(6, 2)               NOT NULL,
    stock              INT                         NOT NULL,
    description        VARCHAR(255),
    creation_date_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    update_date_time   TIMESTAMP WITHOUT TIME ZONE
);
