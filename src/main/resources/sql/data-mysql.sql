DELETE FROM customer;
DELETE FROM account_addresses;
DELETE FROM account_credit_cards;
DELETE FROM credit_card;
DELETE FROM account;
DELETE FROM address;
INSERT INTO account VALUES (0, unix_timestamp(now()), unix_timestamp(now()), 12345, 1, 'user');
SET @account_id = LAST_INSERT_ID();
INSERT INTO address VALUES (0, unix_timestamp(now()), unix_timestamp(now()), 0, 'Palo Alto', 'United States', 'CA', '3495 Deer Creek Road', '', '94304');
SET @address_id = LAST_INSERT_ID();
INSERT INTO account_addresses VALUES (@account_id, @address_id);
INSERT INTO credit_card VALUES (0, unix_timestamp(now()), unix_timestamp(now()), '1234567801234567', 0);
SET @cc_id = LAST_INSERT_ID();
INSERT INTO account_credit_cards VALUES (@account_id, @cc_id);
INSERT INTO customer VALUES (0, unix_timestamp(now()), unix_timestamp(now()), 'john.doe@example.com', 'John', 'Doe', @account_id);

DELETE FROM user;
INSERT INTO user VALUES (0, unix_timestamp(now()), unix_timestamp(now()), 'john.doe@example.com', 'John', 'Doe', 'user');