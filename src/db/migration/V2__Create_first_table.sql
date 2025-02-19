-- Create Customers table
CREATE TABLE Skeleton.Customers
(
    CustomerID  INT IDENTITY (1,1) PRIMARY KEY,
    FirstName   NVARCHAR(50) NOT NULL,
    LastName    NVARCHAR(50) NOT NULL,
    Email       VARCHAR(100) UNIQUE,
    PhoneNumber VARCHAR(20),
    CreatedDate DATETIME2 DEFAULT GETDATE(),
    IsActive    BIT       DEFAULT 1
);