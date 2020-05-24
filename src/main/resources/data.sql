INSERT INTO currency (id, name) VALUES (1L, 'ron');
INSERT INTO currency (id, name) VALUES (2L, 'eur');

INSERT INTO banknote (id, bill_value, currency_id) VALUES (1L, 10L, 1L);
INSERT INTO banknote (id, bill_value, currency_id) VALUES (2L, 50L, 1L);
INSERT INTO banknote (id, bill_value, currency_id) VALUES (3L, 100L, 1L);
INSERT INTO banknote (id, bill_value, currency_id) VALUES (4L, 10L, 2L);
INSERT INTO banknote (id, bill_value, currency_id) VALUES (5L, 50L, 2L);
INSERT INTO banknote (id, bill_value, currency_id) VALUES (6L, 100L, 2L);

INSERT INTO atm_box (id, count_bills, banknote_id) VALUES (1L, 1000L, 1L);
INSERT INTO atm_box (id, count_bills, banknote_id) VALUES (2L, 1000L, 2L);
INSERT INTO atm_box (id, count_bills, banknote_id) VALUES (3L, 1000L, 3L);
INSERT INTO atm_box (id, count_bills, banknote_id) VALUES (4L, 1000L, 4L);
INSERT INTO atm_box (id, count_bills, banknote_id) VALUES (5L, 1000L, 5L);
INSERT INTO atm_box (id, count_bills, banknote_id) VALUES (6L, 1000L, 6L);

INSERT INTO account (id, iban, account_name, username, full_name, card_number, pin_number, balance, bank, swift, bic, currency_id) VALUES
                    (1L, 'RO17INGB5588875882295686', 'Current account 1', 'ion_popescu', 'Ion Mihai Popescu', '4463289457379346' , '1212', 10000L, 'ING BANK', 'INGBROBU', 'INGB', 1L);
INSERT INTO account (id, iban, account_name, username, full_name, card_number, pin_number, balance, bank, swift, bic, currency_id) VALUES
                    (2L, 'RO30INGB2627851689144362', 'Current account 1', 'gigi_ionescu', 'Gigi Dumitru Ionescu', '4432582195877144' , '4567', 12000L, 'ING BANK', 'INGBROBU', 'INGB', 1L);





