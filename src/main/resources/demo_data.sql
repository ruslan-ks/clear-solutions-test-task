INSERT INTO users(first_name, last_name, email, birth_date, phone)
VALUES ('Peter', 'Parker', 'peter@mail.com', '1980-01-10', null),
       ('Joh', 'Snow', 'john@mail.com', '1985-02-10', null),
       ('Sarah', 'Connor', 'sarah@mail.com', '1990-03-10', null);

INSERT INTO user_addresses(user_id, city, street, building, apartment)
VALUES (1, 'New York', 'Ingram Street', 20, null),
       (2, 'New York', 'Washington Street', 150, 75);
