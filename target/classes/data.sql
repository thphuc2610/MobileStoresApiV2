-- Init Category
insert into categories(name) values ('Phone');

-- Init Manufacturer
insert into manufacturers(name) values ('Apple');
insert into manufacturers(name) values ('Samsung');

-- Init Users
-- password là Abc@12345 đã mã hoá
INSERT INTO users (name, username, password, role)
VALUES ('admin', 'admin', '$2a$10$cFrcpAIfkpHhw/nnILqMDeVmzt1ZGFUzydoxUiCNn7Q7Qn/Yuvbaa', 'ADMIN');
