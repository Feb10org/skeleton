CREATE TABLE Skeleton.Products
(
    ProductID     INT IDENTITY (1,1) PRIMARY KEY,
    ProductName   NVARCHAR(100)  NOT NULL,
    Description   NVARCHAR(MAX),
    UnitPrice     DECIMAL(10, 2) NOT NULL CHECK (UnitPrice >= 0),
    StockQuantity INT       DEFAULT 0,
    CategoryID    INT,
    LastModified  DATETIME2 DEFAULT GETDATE()
);