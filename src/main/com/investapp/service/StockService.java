package service; 
import repository.StockRepository;

import java.io.IOException;
import java.net.MalformedURLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

import domain.Stocks; 
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono; 
@Service
public class StockService { 
    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Flux<Stocks> getAll() {
        return stockRepository.getAll();
    }

    public Mono<Stocks> get(String symbol) {
        return stockRepository.get(symbol);
    }
    
    public Mono<Stocks> addStock(String symbol) throws JsonMappingException, JsonProcessingException, MalformedURLException, IOException{   
        ObjectMapper objectMapper = new ObjectMapper();
        HttpHandler handler = new HttpHandler(symbol);   
        Stocks stock = objectMapper.readValue(handler.sendHttp(symbol), Stocks.class); 
        return this.stockRepository.addStock(stock);
    } 

    public Mono<Stocks> deleteStock(String symbol) {   
        return this.stockRepository.deleteStock(symbol);
    }
}
