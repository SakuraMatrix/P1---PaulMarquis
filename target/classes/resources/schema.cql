CREATE KEYSPACE IF NOT EXISTS exchange WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : '1' };

-- Create a table
CREATE TABLE IF NOT EXISTS exchange.stock (
symbol text PRIMARY KEY, 
name text, 
price double,  
changesPercentage double, 
change double, 
dayLow double, 
dayHigh double, 
yearHigh double, 
yearLow double,  
marketCap double, 
priceAvg50 double, 
priceAvg200 double, 
volume double, 
avgVolume double, 
exchange text, 
open double, 
previousClose double, 
eps double, 
pe double, 
earningsAnnouncement text, 
sharesOutstanding double, 
timestamp double

);

-- Insert some data
INSERT INTO exchange.stock
(symbol, price)
VALUES ('TEST', 69 );
