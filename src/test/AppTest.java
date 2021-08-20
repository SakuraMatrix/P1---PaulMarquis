

import static org.junit.Assert.assertNotNull;
import java.io.IOException;
import java.net.MalformedURLException;

import com.datastax.oss.driver.api.core.CqlSession;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.junit.*;

import domain.Stocks;
import reactor.core.publisher.Mono;
import repository.StockRepository;
import service.HttpHandler;
import service.StockService;


public class AppTest {  
    CqlSession session = CqlSession.builder().build();
    HttpHandler handler = new HttpHandler("MSFT");  
    Stocks test = new Stocks("GOOG"); 
    StockRepository testRepo = new StockRepository(session);
    StockService service = new StockService(testRepo);

    

    @Test
    public void sendHttpTest() throws MalformedURLException, IOException { 
        String result = null; 
        result = handler.sendHttp("MSFT"); 
        assertNotNull(result);
    }  

    @Test 
    public void addStock() throws JsonMappingException, JsonProcessingException, MalformedURLException, IOException{ 
        Mono<Stocks> testMono = service.addStock(test.getSymbol()); 
        assertNotNull(testMono);
    } 

    @Test 
    public void deleteStock() throws JsonMappingException, JsonProcessingException, MalformedURLException, IOException{ 
        Mono<Stocks> testMono = service.deleteStock(test.getSymbol()); 
        assertNotNull(testMono);
    }


}   
   
