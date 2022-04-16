CREATE TABLE PRODUCTS
(
    id            INT                         NOT NULL PRIMARY KEY,
    name          VARCHAR(255)                NOT NULL,
    image_uri     VARCHAR(255)                NOT NULL,
    price         NUMERIC(6, 2)               NOT NULL,
    stock         INT                         NOT NULL,
    description   VARCHAR(255),
    creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    update_date   TIMESTAMP WITHOUT TIME ZONE
);
