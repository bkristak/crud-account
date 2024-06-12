USE bank;
SELECT SUM(amount) AS account_balance
FROM transaction
WHERE account_id = ?;

-- @block
USE bank;
SELECT account_name FROM account;