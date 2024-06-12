-- @block
CREATE DATABASE bank;

-- @block
USE bank;
CREATE TABLE account (
  id INT PRIMARY KEY AUTO_INCREMENT,
  account_name VARCHAR(45) UNIQUE NOT NULL,
  created_on DATETIME NOT NULL
);

-- @block
USE bank;
CREATE TABLE transaction (
  id INT PRIMARY KEY AUTO_INCREMENT,
  account_id INT NOT NULL,
  amount DECIMAL(10, 2) NOT NULL,
  created_on DATETIME NOT NULL
  FOREIGN KEY (account_id) REFERENCES account(id)
);