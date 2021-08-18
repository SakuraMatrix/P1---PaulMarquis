package repository;
import com.datastax.oss.driver.api.core.CqlSession;

import domain.Stocks;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
public class StockRepository {
    private CqlSession session; 

    public StockRepository() {
        
    } 

    public StockRepository(CqlSession session) {
        this.session = session;
    } 

    public void test(){ 
        System.out.println("Stock Repository Function");
    }

    public Flux<Stocks> getAll() {
        return Flux.from(session.executeReactive("SELECT * FROM exchange.stock"))
                .map(row -> new Stocks(row.getString("ticker"), row.getDouble("price")));
    }

    public Mono<Stocks> get(String tick) { 
        //System.out.println("SELECT * FROM exchange.stock WHERE ticker = '" + tick.toUpperCase() +"'");
        return Mono.from(session.executeReactive("SELECT * FROM exchange.stock WHERE ticker = '" + tick.toUpperCase() +"'"))
                .map(row -> new Stocks(row.getString("ticker"), row.getDouble("price")));
    } 

    public void addStock(String tick, double price){ 
        session.execute("INSERT INTO exchange.stock (ticker, price) VALUES ('" + tick.toUpperCase() + "', " + price + " );");
    }
}
