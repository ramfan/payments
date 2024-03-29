INSERT INTO credit_type (type) VALUES ('CONSUMER_LOAN') ON CONFLICT DO NOTHING;
INSERT INTO credit_type (type) VALUES ('MORTGAGE') ON CONFLICT DO NOTHING;

INSERT INTO loan_payment_type (type) VALUES ('BASIC') ON CONFLICT DO NOTHING;
INSERT INTO loan_payment_type (type) VALUES ('REDUCTION_OF_LOAN_TERM') ON CONFLICT DO NOTHING;
INSERT INTO loan_payment_type (type) VALUES ('PAYMENT_REDUCTION') ON CONFLICT DO NOTHING;

INSERT INTO role (name) VALUES ('USER') ON CONFLICT DO NOTHING;
INSERT INTO role (name) VALUES ('ADMIN') ON CONFLICT DO NOTHING;

INSERT INTO operation (name) VALUES ('READ') ON CONFLICT DO NOTHING;
INSERT INTO operation (name) VALUES ('WRITE') ON CONFLICT DO NOTHING;
INSERT INTO operation (name) VALUES ('USER_MANAGING') ON CONFLICT DO NOTHING;