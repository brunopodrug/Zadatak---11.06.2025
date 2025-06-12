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