INSERT INTO credit_type (type) VALUES ('CONSUMER_LOAN') ON CONFLICT DO NOTHING;
INSERT INTO credit_type (type) VALUES ('MORTGAGE') ON CONFLICT DO NOTHING;

INSERT INTO loan_payment_type (type) VALUES ('BASIC') ON CONFLICT DO NOTHING;
INSERT INTO loan_payment_type (type) VALUES ('REDUCTION_OF_LOAN_TERM') ON CONFLICT DO NOTHING;
INSERT INTO loan_payment_type (type) VALUES ('PAYMENT_REDUCTION') ON CONFLICT DO NOTHING;