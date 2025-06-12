CREATE TABLE Type
(
    id IDENTITY PRIMARY KEY,
    name        VARCHAR(50)    NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE Hardware
(
    id IDENTITY PRIMARY KEY,
    name        VARCHAR(50)    NOT NULL,
    code        VARCHAR(50)    NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    stock INT,
    typeId  INT,
    FOREIGN KEY (typeId) REFERENCES Type(id)
);

