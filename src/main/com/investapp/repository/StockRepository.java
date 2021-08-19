package repository;
import com.datastax.oss.driver.api.core.CqlSession;

import org.springframework.stereotype.Repository;

import domain.Stocks;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;  
@Repository
public class StockRepository {
    private CqlSession session; 

    public StockRepository(CqlSession session) {
        this.session = session;
    } 

    public void test(){ 
        System.out.println("Stock Repository Function");
    }

    public Flux<Stocks> getAll() {
        return Flux.from(session.executeReactive("SELECT * FROM exchange.stock"))
        .map(row -> new Stocks(row.getString("symbol"), row.getString("name"), row.getDouble("price"), 
        row.getDouble("changesPercentage"), row.getDouble("change"), row.getDouble("dayLow"),  
        row.getDouble("dayHigh"), row.getDouble("yearHigh"), row.getDouble("yearLow"), row.getDouble("marketCap"), 
        row.getDouble("priceAvg50"), row.getDouble("priceAvg200"), row.getDouble("volume"), row.getDouble("avgVolume"), 
        row.getString("exchange"), row.getDouble("open"), row.getDouble("previousClose"), row.getDouble("eps"), 
        row.getDouble("pe"), row.getString("earningsAnnouncement"), row.getDouble("sharesOutstanding"),row.getDouble("timestamp")));
    }

    public Mono<Stocks> get(String symbol) { 
        //System.out.println("SELECT * FROM exchange.stock WHERE ticker = '" + tick.toUpperCase() +"'");
        return Mono.from(session.executeReactive("SELECT * FROM exchange.stock WHERE symbol = '" + symbol.toUpperCase() +"'"))
                .map(row -> new Stocks(row.getString("symbol"), row.getString("name"), row.getDouble("price"), 
                row.getDouble("changesPercentage"), row.getDouble("change"), row.getDouble("dayLow"),  
                row.getDouble("dayHigh"), row.getDouble("yearHigh"), row.getDouble("yearLow"), row.getDouble("marketCap"), 
                row.getDouble("priceAvg50"), row.getDouble("priceAvg200"), row.getDouble("volume"), row.getDouble("avgVolume"), 
                row.getString("exchange"), row.getDouble("open"), row.getDouble("previousClose"), row.getDouble("eps"), 
                row.getDouble("pe"), row.getString("earningsAnnouncement"), row.getDouble("sharesOutstanding"),row.getDouble("timestamp")));
    } 

    public Mono<Stocks> addStock(Stocks stock){ 
        return Mono.from(session.executeReactive("INSERT INTO exchange.stock (symbol, name, price, changesPercentage, change, dayLow," + 
        "dayHigh, yearHigh, yearLow, marketCap, priceAvg50, priceAvg200, volume, avgVolume, " +
        "exchange, open, previousClose, eps, pe, earningsAnnouncement, sharesOutstanding, timestamp )" + 
        " VALUES ('" + stock.getSymbol() + "', '" + stock.getName() + "', " + stock.getPrice() + ", "  +  
        stock.getChangesPercentage() + ", " + stock.getChange() + ", " + stock.getDayLow() + ", " +  
        stock.getDayHigh() + ", " + stock.getYearHigh() + ", " + stock.getYearLow() + ", " +  
        stock.getMarketCap() + ", " + stock.getPriceAvg50() + ", " + stock.getPriceAvg200() + ", " +
        stock.getVolume() + ", " + stock.getAvgVolume() + ", '" + stock.getExchange() +  "', " + 
        stock.getOpen() + ", " + stock.getPreviousClose() + ", " + stock.getEps() + ", " +  
        stock.getPe() + ", '" + stock.getEarningsAnnouncement() + "', " + stock.getSharesOutstanding() + ", " +  
        stock.getTimestamp() + " );"))  
        .map(row -> new Stocks(row.getString("symbol"), row.getString("name"), row.getDouble("price"), 
                row.getDouble("changesPercentage"), row.getDouble("change"), row.getDouble("dayLow"),  
                row.getDouble("dayHigh"), row.getDouble("yearHigh"), row.getDouble("yearLow"), row.getDouble("marketCap"), 
                row.getDouble("priceAvg50"), row.getDouble("priceAvg200"), row.getDouble("volume"), row.getDouble("avgVolume"), 
                row.getString("exchange"), row.getDouble("open"), row.getDouble("previousClose"), row.getDouble("eps"), 
                row.getDouble("pe"), row.getString("earningsAnnouncement"), row.getDouble("sharesOutstanding"),row.getDouble("timestamp"))); 
        
    } 

    public Mono<Stocks> deleteStock(String symbol){ 
        return Mono.from(session.executeReactive("DELETE FROM exchange.stock WHERE symbol = '" + symbol.toUpperCase() + "' IF EXISTS;")) 
        .map(row -> new Stocks(row.getString("symbol"), row.getString("name"), row.getDouble("price"), 
                row.getDouble("changesPercentage"), row.getDouble("change"), row.getDouble("dayLow"),  
                row.getDouble("dayHigh"), row.getDouble("yearHigh"), row.getDouble("yearLow"), row.getDouble("marketCap"), 
                row.getDouble("priceAvg50"), row.getDouble("priceAvg200"), row.getDouble("volume"), row.getDouble("avgVolume"), 
                row.getString("exchange"), row.getDouble("open"), row.getDouble("previousClose"), row.getDouble("eps"), 
                row.getDouble("pe"), row.getString("earningsAnnouncement"), row.getDouble("sharesOutstanding"),row.getDouble("timestamp")));
    }
}
