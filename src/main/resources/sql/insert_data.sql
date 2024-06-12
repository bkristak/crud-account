-- @block
USE bank;
INSERT INTO TABLE account (account_name) VALUES (?);

-- @block
USE bank;
INSERT INTO TABLE transaction (account_id) VALUES (?);