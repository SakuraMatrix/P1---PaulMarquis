CREATE KEYSPACE IF NOT EXISTS exchange WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : '1' };

-- Create a table
CREATE TABLE IF NOT EXISTS exchange.stock (
ticker text PRIMARY KEY,
price double, 
);

-- Insert some data
INSERT INTO exchange.stock
(ticker, price)
VALUES ('MSFT', 324 );
INSERT INTO exchange.stock
(ticker, price)
VALUES ('GOOG', 70 ); 
INSERT INTO exchange.stock
(ticker, price)
VALUES ('TSLA', 255.85 );