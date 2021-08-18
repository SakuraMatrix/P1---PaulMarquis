package service; 
import repository.StockRepository; 
import domain.Stocks;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class StockService { 
    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Flux<Stocks> getAll() {
        return stockRepository.getAll();
    }

    public Mono<Stocks> get(String ticker) {
        return stockRepository.get(ticker);
    }
    
    public void addStock(String tick, double price){ 
        this.stockRepository.addStock(tick, price);
    }
}
