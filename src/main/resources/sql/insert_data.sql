-- @block
USE bank;
INSERT INTO TABLE account (account_name, created_on) VALUES (?, ?);

-- @block
USE bank;
INSERT INTO TABLE transaction (account_id, amount, created_on) VALUES (?, ?, ?);