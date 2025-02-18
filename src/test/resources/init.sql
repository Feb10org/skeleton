
CREATE TABLE Users (
                       id INT PRIMARY KEY IDENTITY,
                       username NVARCHAR(50) NOT NULL,
                       email NVARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE Orders (
                        id INT PRIMARY KEY IDENTITY,
                        user_id INT NOT NULL,
                        total_amount FLOAT NOT NULL,
                        FOREIGN KEY(user_id) REFERENCES Users(id)
);

CREATE INDEX idx_users_email ON Users(email);


INSERT INTO Users (username, email) VALUES ('admin', 'admin@example.com');
INSERT INTO Users (username, email) VALUES ('tester', 'tester@example.com');


INSERT INTO Orders (user_id, total_amount)
VALUES ((SELECT id FROM Users WHERE email = 'admin@example.com'), 500.0);
