INSERT INTO Hardware_Type(name, description)
VALUES('CPU', 'Central processing unit');

INSERT INTO Hardware_Type(name, description)
VALUES('GPU', 'Graphics processing unit');

INSERT INTO Hardware_Type(name, description)
VALUES('MBO', 'Motherboard');

INSERT INTO Hardware_Type(name, description)
VALUES('RAM', 'Random Access Memory');

INSERT INTO Hardware_Type(name, description)
VALUES('STORAGE', 'Storage for data');

INSERT INTO Hardware_Type(name, description)
VALUES('OTHER', 'Other');

INSERT INTO Hardware(name, code, price, stock, type_id)
VALUES('Asus TUF RTX 3080', '1234561', 1599.00, 10 , 2);

INSERT INTO Hardware(name, code, price, stock, type_id)
VALUES('EVGA XC3 RTX 3070 Ti', '1234562', 1299.00, 25 , 2);

INSERT INTO Hardware(name, code, price, stock, type_id)
VALUES('AMD Ryzen 5950X', '1234563', 1599.00, 50 , 1);

INSERT INTO Hardware(name, code, price, stock, type_id)
VALUES('Samsung 980 PRO SSD 1TB', '1234564', 299.00, 200 , 5);

INSERT INTO Hardware(name, code, price, stock, type_id)
VALUES('Kingston FURY Beast DDR5 32GB', '1234565', 699.00, 1000 , 4);

insert into USERS(id, username, password)
values
    (1, 'user', '$2a$12$h0HcS2QDb/7zPASbLa2GoOTSRP6CWK0oX7pCK.dPjkM6L5N4pNovi'), -- password = user
    (2, 'admin', '$2a$12$INo0nbj40sQrTB7b28KJput/bNltGmFyCfRsUhvy73qcXo5/XdsTG'); -- password = admin

insert into AUTHORITY (id, authority_name) values (1, 'ROLE_ADMIN'), (2, 'ROLE_USER');

insert into USERS_AUTHORITY (user_id, authority_id)
values
    (1, 2),
    (2, 1);