CREATE TABLE Orders (
                        id INT PRIMARY KEY IDENTITY,
                        total_amount FLOAT NOT NULL,
);

INSERT INTO Orders (total_amount)
VALUES (500.0);
